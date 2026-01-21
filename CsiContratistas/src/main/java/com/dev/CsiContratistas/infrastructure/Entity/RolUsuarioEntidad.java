package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Rol_usuario;
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
@Table(name = "ROL_USUARIO",schema = "\"gestionUsuarios\"")
public class RolUsuarioEntidad  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rolusuario")
    private Integer id_rolUsuario;

    @ManyToOne
    @JoinColumn(name = "id_rol",nullable = false)
    private RolEntidad id_rol;

    @ManyToOne
    @JoinColumn(name = "id_usuario",nullable = false)
    private UsuarioEntidad id_usuario;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fecha_asignacion= LocalDateTime.now();

    @Column(name = "estado")
    private Boolean estado=true;
    @PrePersist
    protected void prePersist() {
        if (this.fecha_asignacion == null) {
            this.fecha_asignacion = LocalDateTime.now();
        }
        if (this.estado == null) {
            this.estado = true;
        }
    }



    public static RolUsuarioEntidad fromDomainModel(Rol_usuario rolUsuario) {
        return new RolUsuarioEntidad(

                rolUsuario.getId_rol_usuario(),
                RolEntidad.fromDomainModel(rolUsuario.getId_rol()),
                UsuarioEntidad.fromDomainModel(rolUsuario.getId_usuario()),
                rolUsuario.getFecha_asignacion(),
                rolUsuario.getEstado()

        );
    }


    public Rol_usuario toDomainModel (){

        return new Rol_usuario(id_rolUsuario,id_rol.toDomainModel(),id_usuario.toDomainModel(),fecha_asignacion,estado);

    }

}