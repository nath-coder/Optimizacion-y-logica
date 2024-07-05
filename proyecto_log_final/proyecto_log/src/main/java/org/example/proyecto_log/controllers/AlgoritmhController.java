package org.example.proyecto_log.controllers;


import org.example.proyecto_log.services.AlgorithmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlgoritmhController {

    private final AlgorithmService algorithmService;

    public AlgoritmhController(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;

    }

    @GetMapping("moea/run")
    public String init(){
        algorithmService.init();
        return  algorithmService.runn();
    }

}
