package org.example.proyecto_log.model.dto;

public class StopDTO {

    private Long id;
    private String name;
    private String city;
    private String province;
    private String latitud;
    private String longitud;

    // Constructor sin argumentos
    public StopDTO() {}

    // Constructor con argumentos
    public StopDTO(Long id, String name, String city, String province, String latitud, String longitud) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.province = province;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
    @Override
    public String toString(){
        return  "Stop of "+name+" "+latitud+" "+longitud+" "+province+"\n";
    }
}