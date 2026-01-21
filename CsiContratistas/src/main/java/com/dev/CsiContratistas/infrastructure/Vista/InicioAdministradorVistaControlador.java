package com.dev.CsiContratistas.infrastructure.Vista;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administrador/inicio")
public class InicioAdministradorVistaControlador {

    @GetMapping
    public String inicio(){

        return "dashboard/index";
    }

}
