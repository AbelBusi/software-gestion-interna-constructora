package com.dev.CsiContratistas.infrastructure.dto.Empleado;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearEmpleadoDTO {

    private String dni;
    private String nombre;
    private String apellidos;
    private LocalDate fecha_nacimiento;
    private String genero;
    private String telefono;
    private String estado_civil;
    private String direccion;
    private String estado;

}
