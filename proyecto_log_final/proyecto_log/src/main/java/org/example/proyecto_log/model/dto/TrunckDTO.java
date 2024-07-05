package org.example.proyecto_log.model.dto;


public class TrunckDTO {

    private Long id;

    private Long idStopParking;
    private String category;
    private Double capacity;

    // Constructor sin argumentos
    public TrunckDTO() {}

    // Constructor con argumentos
    public TrunckDTO(Long id, Long idStopParking, String category,Double capacity) {
        this.id = id;

        this.idStopParking = idStopParking;
        this.category = category;
        this.capacity = capacity;
    }

    // Getters y Setters

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }
    @Override
    public String toString(){
        return  "DATOS CAMION  id: " + id + "Categoria: " + category + "Capacida: " + capacity +"\n";
    }
}