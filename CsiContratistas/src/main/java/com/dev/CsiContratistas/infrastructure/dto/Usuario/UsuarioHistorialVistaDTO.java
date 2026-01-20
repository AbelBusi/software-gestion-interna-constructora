package com.dev.CsiContratistas.infrastructure.dto.Usuario;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioHistorialVistaDTO {
    private LocalDateTime fecha;
    private Integer idUsuario;
    private String accion;
    private String detalle;
    private String correoResponsable;
    private String nombreResponsable;
    private String rolResponsable;
} 