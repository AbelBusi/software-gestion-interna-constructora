package com.dev.CsiContratistas.infrastructure.Vista;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class LoginVistaControlador {

    @GetMapping("/ingresar")
    public String mostrarLogin() {
        return "login/login"; // Thymeleaf buscará login.html en /templates
    }

    @GetMapping("/recuperar-clave")
    public String mostrarRecuperarClave() {
        return "login/recuperarClave"; // Esta vista puedes crearla luego
    }

}