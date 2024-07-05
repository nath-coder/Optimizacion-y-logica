package org.example.proyecto_log.persistence.entity;


import jakarta.persistence.*;

import java.time.LocalTime;


@Entity
@Table(name = "requires")
public class RequireEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_stop_departure", nullable = false)
    private Long idStopDeparture;

    @Column(name = "id_stop_arrival", nullable = false)
    private Long idStopArrival;

    @Column(nullable = false)
    private String category;

    @Column(name = "pickup_time", nullable = false)
    private LocalTime pickupTime;

    @Column(nullable = false)
    private Double loading;

    // Constructor sin argumentos
    public RequireEntity() {}

    // Constructor con argumentos
    public RequireEntity(Long idStopDeparture, Long idStopArrival, String category, LocalTime pickupTime, Double loading) {
        this.idStopDeparture = idStopDeparture;
        this.idStopArrival = idStopArrival;
        this.category = category;
        this.pickupTime = pickupTime;
        this.loading = loading;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdStopDeparture() {
        return idStopDeparture;
    }

    public void setIdStopDeparture(Long idStopDeparture) {
        this.idStopDeparture = idStopDeparture;
    }

    public Long getIdStopArrival() {
        return idStopArrival;
    }

    public void setIdStopArrival(Long idStopArrival) {
        this.idStopArrival = idStopArrival;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Double getLoading() {
        return loading;
    }

    public void setLoading(Double loading) {
        this.loading = loading;
    }
}
