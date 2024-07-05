package org.example.proyecto_log.persistence.repositories;

import org.example.proyecto_log.persistence.entity.FrameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FrameRepository extends PagingAndSortingRepository<FrameEntity, Integer>, CrudRepository<FrameEntity, Integer> {
}
