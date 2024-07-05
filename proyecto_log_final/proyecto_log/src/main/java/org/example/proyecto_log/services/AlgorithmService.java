package org.example.proyecto_log.services;

import jakarta.annotation.PostConstruct;
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
import org.moeaframework.core.variable.Permutation;
import org.moeaframework.util.TypedProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AlgorithmService {
    MoeaProblem moeaProblem;
    private HashMap<Integer, ArrayList<Destino>> map;
    private Map<String,List<RequireDTO>> requires;
    private Map<String,Double> trunckLoading;
    private final FrameRepository frameRepository;
    private final StopRepository stopRepository;
    private final RequireRepository requireRepository;
    private final FrameMapper frameMapper;
    private final StopMapper stopMapper;
    private final RequireMapper requireMapper;
    private final TrunckMapper trunckMapper;
    private final TrunckRepository trunckRepository;

    public AlgorithmService(FrameRepository frameRepository, FrameMapper frameMapper, StopRepository stopRepository, StopMapper stopMapper, RequireMapper requireMapper, RequireRepository requireRepository, TrunckRepository trunckRepository, TrunckMapper trunckMapper) {
        this.frameRepository = frameRepository;
        this.frameMapper = frameMapper;
        this.stopRepository = stopRepository;
        this.stopMapper = stopMapper;
        this.requireRepository = requireRepository;
        this.requireMapper = requireMapper;
        this.trunckRepository = trunckRepository;
        this.trunckMapper = trunckMapper;
    }

    public String runn() {
        init();
        moeaProblem = new MoeaProblem(5, 3, map, requires, trunckLoading);

        // Configuración del algoritmo
        TypedProperties properties = new TypedProperties();
        properties.setInt("populationSize", 100);
        properties.setDouble("sbx.rate", 1.0);
        properties.setDouble("sbx.distributionIndex", 30.0);
        properties.setDouble("pmx.rate", 1.0);
        properties.setDouble("insertion.rate", 1.0);
        properties.setDouble("maxTime", 60000);

        Algorithm algorithm = new NSGAII(
                moeaProblem
        );
        algorithm.run(10);

        final String[] resultado = {""};
        NondominatedPopulation result = algorithm.getResult();

        Map<String, List<TrunckDTO>> camionesPorCategoria = getAllTruncks().stream()
                .collect(Collectors.groupingBy(TrunckDTO::getCategory));

        for (Solution solution : result) {
            for (int i = 0; i < 5; i++) {
                String categoria = getCategoriaPorIndice(i);
                List<TrunckDTO> camiones = camionesPorCategoria.get(categoria);
                if (camiones != null && !camiones.isEmpty()) {
                    TrunckDTO camión = camiones.remove(0); // Selecciona y elimina el primer camión disponible de la categoría
                    resultado[0] += "Camión: " + camión.getId() + " (Categoría: " + camión.getCategory() + ")\n";
                    Permutation permutation = (Permutation) solution.getVariable(i);
                    resultado[0] += "Ruta: " + categoria + '\n';
                    getListStops(permutation, categoria).forEach(stop -> resultado[0] += stop.toString());
                }
            }
            resultado[0] += "Tiempo: " + solution.getObjective(0) + "\n";
            resultado[0] += "Precio total: " + solution.getObjective(1) + "\n";
            resultado[0] += "Lugares no posibles: " + solution.getObjective(2) + "\n";
        }
        return resultado[0];
    }

    private List<StopDTO> getListStops(Permutation permutation, String key) {
        List<StopDTO> stopDTOs = new ArrayList<>();
        for (int i = 0; i < permutation.size(); i++) {
            Integer stop = Math.toIntExact(requires.get(key).get(permutation.get(i)).getIdStopDeparture());
            Optional<StopEntity> stopEntity = stopRepository.findById(stop);
            stopEntity.ifPresent(entity -> stopDTOs.add(stopMapper.stopToStopDTO(entity)));
        }
        return stopDTOs;
    }

    public String init() {
        map = this.matriz();
        requires = this.getRequiresCategory();
        trunckLoading = getLoading();
        return "";
    }

    public List<TrunckDTO> getAllTruncks() {
        Iterator<TrunckEntity> iterator = trunckRepository.findAll().iterator();
        Iterable<TrunckEntity> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(trunckMapper::trunckToDTO)
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
        Iterable<FrameEntity> iterable = () -> iterator;

        return StreamSupport.stream(iterable.spliterator(), false)
                .map(frameMapper::frameToFrameDTO)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public HashMap<Integer, ArrayList<Destino>> matriz() {
        List<FrameDTO> framesList = this.getAllFrames();
        HashMap<Integer, ArrayList<Destino>> map = new HashMap<>();

        for (FrameDTO frame : framesList) {
            int departureId = Math.toIntExact(frame.getIdStopDeparture());
            long between = Duration.between(frame.getDepartureDatetime(), frame.getArrivalDatetime()).toMinutes();
            if (between < 0) between += Duration.ofHours(24).toMinutes();
            Destino destinoNew = new Destino(frame.getIdStopArrival(), between, frame.getPrice());
            map.computeIfAbsent(departureId, k -> new ArrayList<>()).add(destinoNew);
        }
        return map;
    }

    public List<RequireDTO> getAllRequieres() {
        Iterator<RequireEntity> iterator = requireRepository.findAll().iterator();
        Iterable<RequireEntity> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(requireMapper::requiereEntityToDTO)
                .collect(Collectors.toList());
    }

    public Map<String, List<RequireDTO>> getRequiresCategory() {
        return getAllRequieres()
                .stream()
                .collect(Collectors.groupingBy(RequireDTO::getCategory));
    }

    private String getCategoriaPorIndice(int index) {
        switch (index) {
            case 0: return "LÍQUIDO";
            case 1: return "SÓLIDO";
            case 2: return "INFLAMABLE";
            case 3: return "DELICADO";
            case 4: return "PARTES";
            default: return "";
        }
    }
}
