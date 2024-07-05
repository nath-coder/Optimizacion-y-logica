package org.example.proyecto_log.model.Mappers;


import org.example.proyecto_log.model.dto.RequireDTO;
import org.example.proyecto_log.persistence.entity.RequireEntity;
import org.springframework.stereotype.Component;

@Component
public class RequireMapper {
    public RequireDTO requiereEntityToDTO(RequireEntity requireEntity) {
        RequireDTO requireDTO = new RequireDTO();
        requireDTO.setId(requireEntity.getId());
        requireDTO.setCategory(requireEntity.getCategory());
        requireDTO.setIdStopArrival(requireEntity.getIdStopArrival());
        requireDTO.setIdStopDeparture(requireEntity.getIdStopDeparture());
        requireDTO.setLoading(requireEntity.getLoading());
        requireDTO.setPickupTime(requireEntity.getPickupTime());
        return requireDTO;
    }

    public RequireEntity DTOToEntity(RequireDTO requireDTO) {
        RequireEntity requireEntity = new RequireEntity();
        requireEntity.setId(requireDTO.getId());
        requireEntity.setCategory(requireDTO.getCategory());
        requireEntity.setIdStopArrival(requireDTO.getIdStopArrival());
        requireEntity.setIdStopDeparture(requireDTO.getIdStopDeparture());
        requireEntity.setLoading(requireDTO.getLoading());
        requireEntity.setPickupTime(requireDTO.getPickupTime());
        return requireEntity;
    }

}
