package com.dev.CsiContratistas.infrastructure.Vista;

import com.dev.CsiContratistas.application.Services.UsuarioServicio;
import com.dev.CsiContratistas.application.Services.RolUsuarioServicio;
import com.dev.CsiContratistas.domain.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributes {
    private final UsuarioServicio usuarioServicio;
    private final RolUsuarioServicio rolUsuarioServicio;

    public GlobalModelAttributes(UsuarioServicio usuarioServicio, RolUsuarioServicio rolUsuarioServicio) {
        this.usuarioServicio = usuarioServicio;
        this.rolUsuarioServicio = rolUsuarioServicio;
    }

    @ModelAttribute
    public void addUsuarioActual(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombre = "Invitado";
        String rol = "Sin rol";
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String correo = auth.getName();
            Usuario usuario = usuarioServicio.buscarPorCorreo(correo);
            if (usuario != null && usuario.getId_empleado() != null) {
                nombre = usuario.getId_empleado().getNombre() + " " + usuario.getId_empleado().getApellidos();
                rol = rolUsuarioServicio.obtenerRolPorUsuario(usuario.getId_usuario());
            } else {
                nombre = correo;
            }
        }
        model.addAttribute("usuarioActual", nombre);
        model.addAttribute("rolActual", rol);
    }
} 