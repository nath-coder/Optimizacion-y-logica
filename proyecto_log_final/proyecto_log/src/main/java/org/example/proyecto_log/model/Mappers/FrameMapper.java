package org.example.proyecto_log.model.Mappers;


import org.example.proyecto_log.model.dto.FrameDTO;
import org.example.proyecto_log.persistence.entity.FrameEntity;
import org.springframework.stereotype.Component;

@Component
public class FrameMapper{
    public FrameDTO frameToFrameDTO(FrameEntity frameEntity) {
        FrameDTO frameDTO = new FrameDTO();
        frameDTO.setId(frameEntity.getId());
        frameDTO.setIdStopDeparture(frameEntity.getIdStopDeparture());
        frameDTO.setIdStopArrival(frameEntity.getIdStopArrival());
        frameDTO.setDepartureDatetime(frameEntity.getDepartureDatetime());
        frameDTO.setArrivalDatetime(frameEntity.getArrivalDatetime());
        frameDTO.setPrice(frameEntity.getPrice());
        return frameDTO;
    }
    public FrameEntity frameDTOToFrameEntity(FrameDTO frameDTO) {
        FrameEntity frameEntity = new FrameEntity();
        frameEntity.setId(frameDTO.getId());
        frameEntity.setIdStopDeparture(frameDTO.getIdStopDeparture());
        frameEntity.setIdStopArrival(frameDTO.getIdStopArrival());
        frameEntity.setDepartureDatetime(frameDTO.getDepartureDatetime());
        frameEntity.setArrivalDatetime(frameDTO.getArrivalDatetime());
        frameEntity.setPrice(frameDTO.getPrice());
        return frameEntity;
    }


}

