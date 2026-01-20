package com.dev.CsiContratistas.infrastructure.Vista;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorVistaControlador implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        model.addAttribute("codigo", statusCode);

        if (statusCode != null && statusCode == 404) {
            return "errores/error"; // 404.html en /templates
        }

        return "errores/error"; // Página genérica
    }
}