package org.example.proyecto_log.controllers;

import org.example.proyecto_log.commons.Destino;
import org.example.proyecto_log.model.dto.FrameDTO;
import org.example.proyecto_log.services.Services;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Component
@RestController
public class FrameController {
    private final Services services;

    public FrameController(Services services) {
        this.services = services;
    }
    @GetMapping("frames")
    public Page<FrameDTO> getFrames(
            @RequestParam(name= "orderField") String orderField,
            @RequestParam(name= "orderCriterial") String orderCriterial,
            @RequestParam(name= "pageNumber") Integer pageNumber,
            @RequestParam(name= "pageSize") Integer pageSize
    ){
        return this.services.getFrames(orderField,orderCriterial,pageNumber,pageSize);
    }

    @GetMapping("matrizAdyacencia")
    public HashMap<Integer, ArrayList<Destino>> matrizAdyacencia()  {
        return this.services.matriz();
    }

    @PostMapping("frames")
    public FrameDTO addFrame(@RequestBody FrameDTO frameDTO) {
        return this.services.addFrame(frameDTO);
    }


}
