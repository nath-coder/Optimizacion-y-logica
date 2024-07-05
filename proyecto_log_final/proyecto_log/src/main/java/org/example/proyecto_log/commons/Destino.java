package org.example.proyecto_log.commons;

import java.util.Objects;

public class Destino {
    private Long id;
    private Long duration;
    private Double precio;

    public Destino(Long id) {
        this.id = id;
    }

    public Destino(Long id, Long duration, Double precio) {
        this.id = id;
        this.duration = duration;
        this.precio = precio;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Destino otroObjeto = (Destino) obj;
        return id == otroObjeto.id;
    }
}
