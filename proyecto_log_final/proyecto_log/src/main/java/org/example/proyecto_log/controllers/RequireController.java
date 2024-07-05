package org.example.proyecto_log.controllers;


import org.example.proyecto_log.model.dto.RequireDTO;
import org.example.proyecto_log.services.Services;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class RequireController {
    private final Services services;

    public RequireController(Services services) {
        this.services = services;
    }

    @GetMapping("requires")
    public Page<RequireDTO> getRequieres(
            @RequestParam(name= "orderField") String orderField,
            @RequestParam(name= "orderCriterial") String orderCriterial,
            @RequestParam(name= "pageNumber") Integer pageNumber,
            @RequestParam(name= "pageSize") Integer pageSize
    ) {
        return this.services.getRequieres(orderField, orderCriterial, pageNumber, pageSize);
    }
}
