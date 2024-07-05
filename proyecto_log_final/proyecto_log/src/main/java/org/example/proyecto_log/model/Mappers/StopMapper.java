package org.example.proyecto_log.model.Mappers;

import org.example.proyecto_log.model.dto.StopDTO;
import org.example.proyecto_log.persistence.entity.StopEntity;
import org.springframework.stereotype.Component;

@Component
public class StopMapper {
    public StopDTO stopToStopDTO(StopEntity stopEntity) {
        StopDTO stopDTO = new StopDTO();
        stopDTO.setId(stopEntity.getId());
        stopDTO.setName(stopEntity.getName());
        stopDTO.setCity(stopEntity.getCity());
        stopDTO.setLatitud(stopEntity.getLatitud());
        stopDTO.setLongitud(stopEntity.getLongitud());
        stopDTO.setProvince(stopEntity.getProvince());
        return stopDTO;
    }
    public StopEntity stopDTOToStopEntity(StopDTO stopDTO) {
        StopEntity stopEntity = new StopEntity();
        stopEntity.setId(stopDTO.getId());
        stopEntity.setName(stopDTO.getName());
        stopEntity.setCity(stopDTO.getCity());
        stopEntity.setLatitud(stopDTO.getLatitud());
        stopEntity.setLongitud(stopDTO.getLongitud());
        stopEntity.setProvince(stopDTO.getProvince());
        return stopEntity;

    }
}
