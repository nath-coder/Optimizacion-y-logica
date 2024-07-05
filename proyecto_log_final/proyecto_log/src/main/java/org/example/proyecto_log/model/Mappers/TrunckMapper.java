package org.example.proyecto_log.model.Mappers;


import org.example.proyecto_log.model.dto.TrunckDTO;
import org.example.proyecto_log.persistence.entity.TrunckEntity;
import org.springframework.stereotype.Component;

@Component
public class TrunckMapper {
    public TrunckDTO trunckToDTO(TrunckEntity trunckEntity) {
        TrunckDTO trunckDTO = new TrunckDTO();
        trunckDTO.setId(trunckEntity.getId());
        trunckDTO.setCategory(trunckEntity.getCategory());
        trunckDTO.setCapacity(trunckEntity.getCapacity());
        trunckDTO.setIdStopParking(trunckEntity.getIdStopParking());
        return trunckDTO;
    }
    public TrunckEntity trunckDTOToEntity(TrunckDTO trunckDTO) {
        TrunckEntity trunckEntity = new TrunckEntity();
        trunckEntity.setId(trunckDTO.getId());
        trunckEntity.setCategory(trunckDTO.getCategory());
        trunckEntity.setCapacity(trunckDTO.getCapacity());
        trunckEntity.setIdStopParking(trunckDTO.getIdStopParking());
        return trunckEntity;
    }

}
