package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Rol;
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
@Table(name = "ROL",schema = "\"gestionUsuarios\"")
public class RolEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer id_rol;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fecha_asignacion;

    @Column(name = "estado")
    private Boolean estado =true;

    @OneToMany(mappedBy = "id_rol")
    private List<RolUsuarioEntidad> rolesUsuarios = new ArrayList<>();
    @PrePersist
    public void prePersist() {
        if(this.fecha_asignacion == null){
            this.fecha_asignacion = LocalDateTime.now();
        }
        if(this.estado == null){
            this.estado = true;
        }
    }

    public static RolEntidad fromDomainModel(Rol rol){
        return new RolEntidad(
                rol.getId_rol(),
                rol.getNombre(),
                rol.getDescripcion(),
                rol.getFecha_asignacion(),
                rol.getEstado(),
                null

        );
    }

    public Rol toDomainModel (){

        return new Rol(id_rol,nombre,descripcion,fecha_asignacion,estado);

    }


}
