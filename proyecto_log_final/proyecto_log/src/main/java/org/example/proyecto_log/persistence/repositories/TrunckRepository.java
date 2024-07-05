package org.example.proyecto_log.persistence.repositories;

import org.example.proyecto_log.persistence.entity.TrunckEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrunckRepository  extends PagingAndSortingRepository<TrunckEntity, Integer>, CrudRepository<TrunckEntity, Integer> {
}
