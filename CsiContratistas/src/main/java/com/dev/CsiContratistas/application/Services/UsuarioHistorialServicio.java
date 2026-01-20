package com.dev.CsiContratistas.application.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.infrastructure.Entity.UsuarioHistorialEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaUsuarioHistorialRepositorio;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.UsuarioHistorialVistaDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioHistorialServicio {

    private final IJpaUsuarioHistorialRepositorio historialRepositorio;

    // Ya NO inyectes UsuarioServicio ni RolUsuarioServicio aquí para evitar el ciclo

    public void registrarHistorial(Integer idUsuario, String accion, String detalle, String usuarioResponsable) {
        UsuarioHistorialEntidad historial = new UsuarioHistorialEntidad();
        historial.setIdUsuario(idUsuario);
        historial.setAccion(accion);
        historial.setDetalle(detalle);
        historial.setFecha(LocalDateTime.now());
        historial.setUsuarioResponsable(usuarioResponsable);
        historialRepositorio.save(historial);
    }

    public List<UsuarioHistorialEntidad> obtenerHistorialPorUsuario(Integer idUsuario) {
        return historialRepositorio.findByIdUsuarioOrderByFechaDesc(idUsuario);
    }

    public List<UsuarioHistorialEntidad> obtenerHistorialCompleto() {
        return historialRepositorio.findAllByOrderByFechaDesc();
    }

    // Este método recibe los servicios como parámetros → NO crea ciclos
    public List<UsuarioHistorialVistaDTO> obtenerHistorialCompletoVista(
            UsuarioServicio usuarioServicio,
            RolUsuarioServicio rolUsuarioServicio
    ) {

        List<UsuarioHistorialEntidad> historial = obtenerHistorialCompleto();

        return historial.stream().map(item -> {
            String correo = item.getUsuarioResponsable();
            Usuario responsable = usuarioServicio.buscarPorCorreo(correo);

            String nombre = "-";
            if (responsable != null && responsable.getId_empleado() != null) {
                nombre = responsable.getId_empleado().getNombre() + " " +
                        responsable.getId_empleado().getApellidos();
            }

            String rol = "-";
            if (responsable != null) {
                Rol_usuario ru = rolUsuarioServicio
                        .obtenerPorUsuarioId(responsable.getId_usuario())
                        .orElse(null);

                if (ru != null && ru.getId_rol() != null) {
                    rol = ru.getId_rol().getNombre();
                }
            }

            return new UsuarioHistorialVistaDTO(
                    item.getFecha(),
                    item.getIdUsuario(),
                    item.getAccion(),
                    item.getDetalle(),
                    correo,
                    nombre,
                    rol
            );
        }).toList();
    }
}
