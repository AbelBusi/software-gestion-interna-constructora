package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PERMISO",schema ="\"gestionUsuarios\"")
public class PermisoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Integer id_permiso;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fecha_asignacion;

    @Column(name = "estado")
    private Boolean estado =true;

    public static PermisoEntidad fromDomainModel(Permiso permiso){
        return new PermisoEntidad(
                permiso.getId_permiso(),
                permiso.getNombre(),
                permiso.getDescripcion(),
                permiso.getFecha_asignacion(),
                permiso.getEstado()
        );
    }

    public Permiso toDomainModel (){

        return new Permiso(id_permiso,nombre,descripcion,fecha_asignacion,estado);

    }

}
