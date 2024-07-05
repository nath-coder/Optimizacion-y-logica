package org.example.proyecto_log.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "truncks")
public class TrunckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "id_stop_parking", nullable = false)
    private Long idStopParking;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double capacity;

    // Constructor sin argumentos
    public TrunckEntity() {}

    // Constructor con argumentos
    public TrunckEntity( Long idStopParking, String category,Double capacity) {
        this.idStopParking = idStopParking;
        this.category = category;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getIdStopParking() {
        return idStopParking;
    }

    public void setIdStopParking(Long idStopParking) {
        this.idStopParking = idStopParking;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
// Getters y Setters
    // ... (mismos getters y setters que antes)
}