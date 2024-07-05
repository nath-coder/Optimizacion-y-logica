package org.example.proyecto_log.services;

import jakarta.annotation.PostConstruct;
import org.example.proyecto_log.model.Mappers.FrameMapper;
import org.example.proyecto_log.model.Mappers.RequireMapper;
import org.example.proyecto_log.model.Mappers.StopMapper;
import org.example.proyecto_log.model.dto.FrameDTO;
import org.example.proyecto_log.model.dto.RequireDTO;
import org.example.proyecto_log.model.dto.StopDTO;
import org.example.proyecto_log.commons.Destino;
import org.example.proyecto_log.persistence.entity.FrameEntity;
import org.example.proyecto_log.persistence.entity.RequireEntity;
import org.example.proyecto_log.persistence.repositories.FrameRepository;
import org.example.proyecto_log.persistence.repositories.RequireRepository;
import org.example.proyecto_log.persistence.repositories.StopRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class Services {

    private final FrameRepository frameRepository;
    private final StopRepository stopRepository;
    private final RequireRepository requireRepository;
    private final FrameMapper frameMapper;
    private final StopMapper stopMapper;
    private final RequireMapper requireMapper;

    public Services(FrameRepository frameRepository, FrameMapper frameMapper, StopRepository stopRepository, StopMapper stopMapper, RequireMapper requireMapper, RequireRepository requireRepository ){
        this.frameRepository=frameRepository;
        this.frameMapper=frameMapper;
        this.stopRepository=stopRepository;
        this.stopMapper=stopMapper;
        this.requireRepository = requireRepository;
        this.requireMapper=requireMapper;
    }

    public Page<FrameDTO> getFrames(String orderField, String orderCriterial, Integer pageNumber, Integer pageSize) {
        Pageable page=Pageable.ofSize(pageSize);

        return frameRepository
                .findAll(page)
                .map(frameMapper::frameToFrameDTO);
    }

    public Page<StopDTO> getStops(String orderField, String orderCriterial, Integer pageNumber, Integer pageSize) {
        Pageable page=Pageable.ofSize(pageSize);
        return  stopRepository.
                findAll(page)
                .map(stopMapper::stopToStopDTO);
    }

    public Page<RequireDTO>  getRequieres(String orderField, String orderCriterial, Integer pageNumber, Integer pageSize){
        Pageable page=Pageable.ofSize(pageSize);
        return requireRepository.findAll(page).map(requireMapper::requiereEntityToDTO);
    }
    public FrameDTO addFrame(FrameDTO frameDTO){
        return frameMapper.frameToFrameDTO(frameRepository.save(frameMapper.frameDTOToFrameEntity(frameDTO)));
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
}
