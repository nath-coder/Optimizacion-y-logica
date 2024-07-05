package org.example.proyecto_log.model.dto;

import java.time.LocalTime;

public class FrameDTO {


    private long id;
    private Long idStopDeparture;

    private Long idStopArrival;

    private Double price;

    private LocalTime departureDatetime;

    private LocalTime arrivalDatetime;

    // Constructor sin argumentos
    public FrameDTO() {}

    // Constructor con argumentos
    public FrameDTO(Long id,Long idStopDeparture, Long idStopArrival, Double price, LocalTime departureDatetime, LocalTime arrivalDatetime) {
        this.id = id;
        this.idStopDeparture = idStopDeparture;
        this.idStopArrival = idStopArrival;
        this.price = price;
        this.departureDatetime = departureDatetime;
        this.arrivalDatetime = arrivalDatetime;
    }

    // Getters y Setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalTime getDepartureDatetime() {
        return departureDatetime;
    }

    public void setDepartureDatetime(LocalTime departureDatetime) {
        this.departureDatetime = departureDatetime;
    }

    public LocalTime getArrivalDatetime() {
        return arrivalDatetime;
    }

    public void setArrivalDatetime(LocalTime arrivalDatetime) {
        this.arrivalDatetime = arrivalDatetime;
    }
}