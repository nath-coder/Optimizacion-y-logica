package org.example.proyecto_log.controllers;

import org.example.proyecto_log.model.dto.StopDTO;
import org.example.proyecto_log.services.Services;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StopController {
    private final Services services;

    public StopController(Services services) {
        this.services = services;
    }

    @GetMapping("stop")
    public Page<StopDTO> getFrames(
            @RequestParam(name= "orderField") String orderField,
            @RequestParam(name= "orderCriterial") String orderCriterial,
            @RequestParam(name= "pageNumber") Integer pageNumber,
            @RequestParam(name= "pageSize") Integer pageSize
    ){
        return this.services.getStops(orderField,orderCriterial,pageNumber,pageSize);
    }
}
