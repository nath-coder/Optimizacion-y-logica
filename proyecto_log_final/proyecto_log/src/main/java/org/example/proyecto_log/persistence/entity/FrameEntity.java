package org.example.proyecto_log.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalTime;


@Entity
@Table(name = "frames")
public class FrameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_stop_departure", nullable = false)
    private Long idStopDeparture;

    @Column(name = "id_stop_arrival", nullable = false)
    private Long idStopArrival;

    @Column(nullable = false)
    private Double price;

    @Column(name = "departure_datetime", nullable = false)
    private LocalTime departureDatetime;

    @Column(name = "arrival_datetime", nullable = false)
    private LocalTime arrivalDatetime;



    // Constructor sin argumentos
    public FrameEntity() {}

    // Constructor con argumentos
    public FrameEntity(Long idStopDeparture, Long idStopArrival, Double price, LocalTime departureDatetime, LocalTime arrivalDatetime) {
        this.idStopDeparture = idStopDeparture;
        this.idStopArrival = idStopArrival;
        this.price = price;
        this.departureDatetime = departureDatetime;
        this.arrivalDatetime = arrivalDatetime;
    }

    // Getters y Setters




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


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}