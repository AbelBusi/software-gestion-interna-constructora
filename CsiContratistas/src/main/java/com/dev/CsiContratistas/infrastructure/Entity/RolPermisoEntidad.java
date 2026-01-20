package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Rol_Permiso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROL_PERMISO",schema = "\"gestionUsuarios\"")
public class RolPermisoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_permiso")
    private Integer id_rol_permiso;

    @ManyToOne
    @JoinColumn(name = "id_rol",nullable = false)
    private RolEntidad id_rol;

    @ManyToOne
    @JoinColumn(name ="id_permiso",nullable = false)
    private PermisoEntidad id_permiso;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fecha_asignacion;

    @Column(name = "estado")
    private Boolean estado;

    public static RolPermisoEntidad fromDomainModel(Rol_Permiso rolPermiso) {
        return new RolPermisoEntidad(

                rolPermiso.getId_rol_Permiso(),
                RolEntidad.fromDomainModel(rolPermiso.getId_rol()),
                PermisoEntidad.fromDomainModel(rolPermiso.getId_permiso()),
                rolPermiso.getFecha_asignacion(),
                rolPermiso.getEstado()

        );
    }


    public Rol_Permiso toDomainModel (){

        return new Rol_Permiso(id_rol_permiso,id_rol.toDomainModel(),id_permiso.toDomainModel(),fecha_asignacion,estado);

    }

}