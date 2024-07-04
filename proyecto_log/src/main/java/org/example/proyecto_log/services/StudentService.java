package org.example.proyecto_log.services;

import org.example.proyecto_log.persistence.entity.StudentEntity;
import org.example.proyecto_log.persistence.repositories.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.proyecto_log.persistence.entity.StudentEntity;

import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }


    public Page<StudentEntity> getStudents(String orderField, String orderCriterial, Integer pageNumber, Integer pageSize) {
        Pageable page=Pageable.ofSize(pageSize);
        return studentRepository.findAll(page);
    }

    public StudentEntity addStudent(StudentEntity studentEntity){
        return studentRepository.save(studentEntity);
    }
    public void deleteStudent(Integer id){
        studentRepository.deleteById(id);
    }
    public StudentEntity updateStudent(StudentEntity studentEntity){
        Long id=studentEntity.getId();
        Optional<StudentEntity> studentOld=studentRepository.findById(Math.toIntExact(id));
        if(studentEntity.getNationality()==null)
            studentEntity.setNationality(studentOld.get().getNationality());
        if(studentEntity.getBirthdate()==null)
            studentEntity.setBirthdate(studentOld.get().getBirthdate());
        if(studentEntity.getResidence()==null)
            studentEntity.setResidence(studentOld.get().getResidence());
        if(studentEntity.getName()==null)
            studentEntity.setName(studentOld.get().getName());
        studentRepository.save(studentEntity);
        return studentEntity;
    }
}
