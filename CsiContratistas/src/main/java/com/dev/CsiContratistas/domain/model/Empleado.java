package com.dev.CsiContratistas.domain.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empleado implements Serializable{

    private Integer id_empleado;
    private String dni;
    private String nombre;
    private String apellidos;
    private LocalDate fecha_nacimiento;
    private String genero;
    private String telefono;
    private String estado_civil;
    private String direccion;
    private String estado;

    @Getter
    @Setter
    @NoArgsConstructor 
    public static class EmpleadoVista {
        private Integer id_empleado;
        private String nombre;
        private String apellidos;
        private String dni;
        private LocalDate fecha_nacimiento;
        private String genero;
        private String telefono;
        private String estado_civil;
        private String direccion;
        private String estado;
        private String profesionNombre;
        private String ramaNombre;
        private Integer profesionId; 
        private Integer ramaId;      
        private Integer cargoId;     

        public EmpleadoVista(Integer id_empleado, String nombre, String apellidos, String dni, LocalDate fecha_nacimiento,
                             String genero, String telefono, String estado_civil, String direccion, String estado,
                             String profesionNombre, String ramaNombre, Integer profesionId, Integer ramaId, Integer cargoId) {
            this.id_empleado = id_empleado;
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.dni = dni;
            this.fecha_nacimiento = fecha_nacimiento;
            this.genero = genero;
            this.telefono = telefono;
            this.estado_civil = estado_civil;
            this.direccion = direccion;
            this.estado = estado;
            this.profesionNombre = profesionNombre;
            this.ramaNombre = ramaNombre;
            this.profesionId = profesionId;
            this.ramaId = ramaId;
            this.cargoId = cargoId;
        }
    }
}