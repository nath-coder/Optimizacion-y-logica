package org.example.proyecto_log.services;

import jakarta.annotation.PostConstruct;
import org.apache.commons.math3.genetics.TournamentSelection;
import org.example.proyecto_log.commons.Destino;
import org.example.proyecto_log.commons.MoeaProblem;
import org.example.proyecto_log.model.Mappers.FrameMapper;
import org.example.proyecto_log.model.Mappers.RequireMapper;
import org.example.proyecto_log.model.Mappers.StopMapper;
import org.example.proyecto_log.model.Mappers.TrunckMapper;
import org.example.proyecto_log.model.dto.FrameDTO;
import org.example.proyecto_log.model.dto.RequireDTO;
import org.example.proyecto_log.model.dto.StopDTO;
import org.example.proyecto_log.model.dto.TrunckDTO;
import org.example.proyecto_log.persistence.entity.FrameEntity;
import org.example.proyecto_log.persistence.entity.RequireEntity;
import org.example.proyecto_log.persistence.entity.StopEntity;
import org.example.proyecto_log.persistence.entity.TrunckEntity;
import org.example.proyecto_log.persistence.repositories.*;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.operator.permutation.Insertion;
import org.moeaframework.core.operator.permutation.PMX;
import org.moeaframework.core.variable.Permutation;
import org.moeaframework.util.TypedProperties;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class AlgorithmService {
    MoeaProblem moeaProblem;
    private HashMap<Integer, ArrayList<Destino>> map;
    private Map<String,List<RequireDTO>> requires;
    private Map<String,Double> trunckLoading;
    private List<StopDTO> stops;
    private final FrameRepository frameRepository;
    private final StopRepository stopRepository;
    private final RequireRepository requireRepository;
    private final FrameMapper frameMapper;
    private final StopMapper stopMapper;
    private final StudentRepository studentRepository;
    private final RequireMapper requireMapper;
    private final TrunckMapper trunckMapper;
    private final TrunckRepository trunckRepository;


    public AlgorithmService(FrameRepository frameRepository, FrameMapper frameMapper, StopRepository stopRepository, StopMapper stopMapper, StudentRepository studentRepository, RequireMapper requireMapper, RequireRepository requireRepository, TrunckRepository trunckRepository, TrunckMapper trunckMapper){
        this.frameRepository=frameRepository;
        this.frameMapper=frameMapper;
        this.stopRepository=stopRepository;
        this.stopMapper=stopMapper;
        this.studentRepository = studentRepository;
        this.requireRepository = requireRepository;
        this.requireMapper=requireMapper;
        this.trunckRepository = trunckRepository;
        this.trunckMapper=trunckMapper;
    }
    public String runn(){
        init();
        moeaProblem=new MoeaProblem(5,3,map,requires,trunckLoading);
        //configuracion del algoritmo
        TypedProperties properties = new TypedProperties();
        properties.setInt("populationSize", 100);
        properties.setDouble("sbx.rate", 1.0);
        properties.setDouble("sbx.distributionIndex", 30.0);
        properties.setDouble("pmx.rate", 1.0);
        properties.setDouble("insertion.rate", 1.0);

        Algorithm algorithm = new NSGAII(
                moeaProblem
        );
        algorithm.run(100000);

        final String[] resultado = {""};
        NondominatedPopulation result = algorithm.getResult();
        ArrayList<String> key = new ArrayList<>(Arrays.asList("LÍQUIDO", "SÓLIDO", "INFLAMABLE", "DELICADO", "PARTES"));
        // Imprimir las soluciones no dominadas
        for (Solution solution : result) {

            for(int i=0; i<5; i++){
                Permutation permutation=(Permutation) solution.getVariable(i);
                resultado[0] +="Ruta of category: "+key.get(i)+": ";
                    getListStops(permutation, key.get(i)).forEach(stop -> resultado[0] += stop.toString());
            }

            resultado[0] +=" Tiempo: " + solution.getObjective(0) +"\n";
            resultado[0] +="Precio toal: "+ solution.getObjective(1)+"\n";
            resultado[0] +="Lugares no posible"+solution.getObjective(2)+"\n";
        }


        return resultado[0];
    }

    private List<StopDTO> getAllStops(){
        Iterator<StopEntity> iterator =stopRepository.findAll().iterator();

        // Convertir el Iterator a un Stream
        Iterable<StopEntity> iterable = () -> iterator;
        List<StopDTO> list = StreamSupport.stream(iterable.spliterator(), false)
                .map(stopMapper::stopToStopDTO).collect(Collectors.toList());

        return list;
    }
    private List<StopDTO> getListStops(Permutation permutation,String key) {
        List<StopDTO> stopDTOs = new ArrayList<>();
        for(int i=0; i<permutation.size(); i++){
            Integer stop=Math.toIntExact(requires.get(key).get(permutation.get(i)).getIdStopDeparture());
            Optional<StopEntity> stopEntity=stopRepository.findById(stop);
            if(stopEntity.isPresent()){
                stopDTOs.add(stopMapper.stopToStopDTO(stopEntity.get()));
            }
        }
        return stopDTOs;
    }


    public String init(){
        stops=getAllStops();
         map= this.matriz();
        requires= this.getRequiresCategory();
        trunckLoading=getLoading();
        return "";
    }

    public List<TrunckDTO> getAllTruncks(){
        Iterator<TrunckEntity> iterator = trunckRepository.findAll().iterator();

        Iterable<TrunckEntity> iterable=()->iterator;

        Stream<TrunckEntity> stream=StreamSupport.stream(iterable.spliterator(),false);

        return stream.map(trunckMapper::trunckToDTO)
                .collect(Collectors.toList());

    }

    public Map<String, Double> getLoading() {
        return getAllTruncks()
                .stream()
                .collect(Collectors.groupingBy(
                        TrunckDTO::getCategory,
                        Collectors.summingDouble(TrunckDTO::getCapacity)
                ));
    }

    public List<FrameDTO> getAllFrames() {
        Iterator<FrameEntity> iterator = frameRepository.findAll().iterator();

        // Convertir el Iterator a un Stream
        Iterable<FrameEntity> iterable = () -> iterator;
        Stream<FrameEntity> stream = StreamSupport.stream(iterable.spliterator(), false);

        return stream
                .map(frameMapper::frameToFrameDTO)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public HashMap<Integer, ArrayList< Destino>> matriz() {

        List<FrameDTO> framesList = this.getAllFrames();
        HashMap<Integer,ArrayList< Destino>> map = new HashMap<>();

        for (FrameDTO frame : framesList) {
            int departureId = Math.toIntExact(frame.getIdStopDeparture());
            long between = Duration.between(frame.getDepartureDatetime(), frame.getArrivalDatetime()).toMinutes();
            if(between<0)
                between += Duration.ofHours(24).toMinutes();
            Destino destinoNew=new Destino(frame.getIdStopArrival(), between, frame.getPrice());
            // Verificar si la clave ya está en el mapa
            if (!map.containsKey(departureId)) {
                ArrayList< Destino> destinos = new ArrayList<>();
                destinos.add(destinoNew);
                map.put(departureId,destinos);
            }else{
                ArrayList< Destino> destinos=map.get(departureId);
                if(!destinos.contains(destinoNew)){
                    destinos.add(destinoNew);
                }
            }

        }
        return map;
    }

    public List<RequireDTO> getAllRequieres() {
        Iterator<RequireEntity> iterator = requireRepository.findAll().iterator();

        // Convertir el Iterator a un Stream
        Iterable<RequireEntity> iterable = () -> iterator;
        Stream<RequireEntity> stream = StreamSupport.stream(iterable.spliterator(), false);

        return stream
                .map(requireMapper::requiereEntityToDTO)
                .collect(Collectors.toList());
    }
    public Map<String,List<RequireDTO>> getRequiresCategory() {
       return getAllRequieres()
                .stream()
                .collect(Collectors.groupingBy(RequireDTO::getCategory));

    }
}
