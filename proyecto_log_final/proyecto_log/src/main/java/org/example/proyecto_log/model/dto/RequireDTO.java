package org.example.proyecto_log.model.dto;

import java.time.LocalTime;


public class RequireDTO{


    private Long id;
    private Long idStopDeparture;
    private Long idStopArrival;
    private String category;
    private LocalTime pickupTime;
    private Double loading;

    // Constructor sin argumentos
    public RequireDTO() {}

    // Constructor con argumentos
    public RequireDTO(Long idStopDeparture, Long idStopArrival, String category, LocalTime pickupTime, Double loading) {
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
