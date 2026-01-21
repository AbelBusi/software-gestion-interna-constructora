package com.dev.CsiContratistas.infrastructure.Vista;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LandingPageVistaControlador {

    @GetMapping({"", "/inicio"})
    public String mostrarInicio() {
        return "Landing Page/index";
    }

    @GetMapping("/galeria")
    public String mostrarGaleria() {
        return "Landing Page/galeria";
    }

    @GetMapping("/servicios")
    public String mostrarServicios() {
        return "Landing Page/servicios";
    }

    @GetMapping("/inmuebles")
    public String mostrarInmuebles() {
        return "Landing Page/inmuebles";
    }

    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "Landing Page/contacto";
    }
}
