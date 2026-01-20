package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Cargo;
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
@Table(name = "CARGO",schema ="\"gestionEmpleados\"")
public class CargoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cargo")
    private Integer id_cargo;

    @ManyToOne
    @JoinColumn(name = "id_empleado",nullable = false)
    private EmpleadoEntidad id_empleado;

    @ManyToOne
    @JoinColumn(name = "id_rama",nullable = false)
    private RamaEntidad id_rama;

    @ManyToOne
    @JoinColumn(name = "id_profesion")
    private ProfesionEntidad id_profesion;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fecha_asignacion;

    @Column(name = "estado")
    private Boolean estado = true;


    public static CargoEntidad fromDomainModel(Cargo cargo) {
        return new CargoEntidad(
                cargo.getId_cargo(),
                EmpleadoEntidad.fromDomainModel(cargo.getId_empleado()),
                RamaEntidad.fromDomainModel(cargo.getId_rama()) ,
                ProfesionEntidad.soloConId(cargo.getId_profesion().getId_profesion()),
                cargo.getFecha_asignacion(),
                cargo.getEstado()
        );
    }


    public Cargo toDomainModel (){

        return new Cargo(id_cargo,id_empleado.toDomainModel(),id_rama.toDomainModel(),id_profesion.toDomainModel(),fecha_asignacion,estado);

    }

}