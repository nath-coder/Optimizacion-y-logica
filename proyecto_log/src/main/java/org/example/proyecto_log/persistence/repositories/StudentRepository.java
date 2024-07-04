package org.example.proyecto_log.persistence.repositories;

import org.example.proyecto_log.persistence.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<StudentEntity, Integer> , CrudRepository<StudentEntity, Integer> {


}
