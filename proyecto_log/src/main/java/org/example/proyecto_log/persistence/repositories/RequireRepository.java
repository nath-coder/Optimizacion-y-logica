package org.example.proyecto_log.persistence.repositories;

import org.example.proyecto_log.persistence.entity.RequireEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequireRepository extends PagingAndSortingRepository<RequireEntity, Integer>, CrudRepository<RequireEntity, Integer> {
}
