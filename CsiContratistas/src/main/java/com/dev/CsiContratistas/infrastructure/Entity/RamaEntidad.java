package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Rama;
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
@Table(name = "RAMA",schema = "\"gestionEmpleados\"")
public class RamaEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rama")
    private Integer id_rama;

    @ManyToOne
    @JoinColumn(name = "id_profesion",nullable = false)
    private ProfesionEntidad id_profesion;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fecha_asignacion;

    @Column(name = "estado")
    private Boolean estado=true;

    @OneToMany(mappedBy = "id_rama",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CargoEntidad> cargos = new ArrayList<>();


    public static RamaEntidad fromDomainModel(Rama rama){
        return new RamaEntidad(
                rama.getId_rama(),
                ProfesionEntidad.fromDomainModel(rama.getId_profesion()),
                rama.getNombre(),
                rama.getDescripcion(),
                rama.getFecha_asignacion(),
                rama.getEstado(),
                new ArrayList<>()
        );
    }

    public Rama toDomainModel (){
        return new Rama(
                id_rama,
                id_profesion != null ? id_profesion.toDomainModel() : null,
                nombre,
                descripcion,
                fecha_asignacion,
                estado
        );
    }

}
