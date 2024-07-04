package org.example.proyecto_log.controllers;

import org.example.proyecto_log.persistence.entity.StudentEntity;
import org.example.proyecto_log.services.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("student")
    public Page<StudentEntity> getStudents(
        @RequestParam(name= "orderField") String orderField,
        @RequestParam(name= "orderCriterial") String orderCriterial,
        @RequestParam(name= "pageNumber") Integer pageNumber,
        @RequestParam(name= "pageSize") Integer pageSize
    ){
        return this.studentService.getStudents(orderField, orderCriterial, pageNumber, pageSize);
    }

    @PostMapping("student")
    public StudentEntity postStudents(@RequestBody StudentEntity student){
        return studentService.addStudent(student);  
    }

    @PutMapping("student")
    public String putStudents(){
        return "put Students";
    }

    @DeleteMapping("student")
    public void deleteStudents(@RequestParam Integer id){
        studentService.deleteStudent(id);
    }
    @PatchMapping("student")
    public StudentEntity patchStudents( @RequestBody StudentEntity student){
       return studentService.updateStudent(student);
    }
}
