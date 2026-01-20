package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Profesiones;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PROFESIONES", schema = "\"gestionEmpleados\"")
public class ProfesionEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesion")
    private Integer id_profesion;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fecha_asignacion;

    @Column(name = "estado")
    private Boolean estado = true;

    @OneToMany(mappedBy = "id_profesion", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<RamaEntidad> ramas = new ArrayList<>();

    @OneToMany(mappedBy = "id_profesion", cascade = CascadeType.ALL)
    private List<CargoEntidad> cargos = new ArrayList<>();

    @PrePersist
    public void asignarFechaAsignacion() {
        if (this.fecha_asignacion == null) {
            this.fecha_asignacion = java.time.LocalDateTime.now();
        }
    }

    public ProfesionEntidad(Integer id_profesion, String nombre, String descripcion, LocalDateTime fecha_asignacion,
            Boolean estado) {
        this.id_profesion = id_profesion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_asignacion = fecha_asignacion;
        this.estado = estado;
    }

    public static ProfesionEntidad fromDomainModel(Profesiones profesiones) {
        if (profesiones == null) {
            return null;
        }
        return new ProfesionEntidad(
                profesiones.getId_profesion(),
                profesiones.getNombre(),
                profesiones.getDescripcion(),
                profesiones.getFecha_asignacion(),
                profesiones.getEstado());
    }

    public static ProfesionEntidad soloConId(Integer id) {
        ProfesionEntidad entidad = new ProfesionEntidad();
        entidad.setId_profesion(id);
        return entidad;
    }

    public Profesiones toDomainModel() {

        return new Profesiones(id_profesion, nombre, descripcion, fecha_asignacion, estado);

    }

}