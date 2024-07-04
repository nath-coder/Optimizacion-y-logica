package org.example.proyecto_log.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="students")
@Getter
@Setter
@NoArgsConstructor

public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthdate;
    private String nationality;
    private String residence;

    //@Column(name = "RESICENCE", columnDefinition = "Nva")

}
