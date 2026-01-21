package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Empleado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLEADO",schema ="\"gestionEmpleados\"")
public class EmpleadoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer id_empleado;

    @Column(name = "dni",length = 8)
    private String dni;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private LocalDate fecha_nacimiento;

    @Column(name = "genero",length = 10)
    private String genero;

    @Column(name = "telefono", length = 9)
    private String telefono;

    @Column(name = "estado_civil")
    private String estado_civil;

    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "estado")
    private String estado = "Activo";

    @OneToMany(mappedBy = "id_empleado", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CargoEntidad> cargos=new ArrayList<>();

    @OneToOne(mappedBy = "id_empleado", cascade = CascadeType.ALL)
    private UsuarioEntidad usuario;

    @OneToMany(mappedBy = "id_responsable",cascade = CascadeType.ALL)
    private List<ObraEntidad> responsable = new ArrayList<>();


    public static EmpleadoEntidad fromDomainModel(Empleado empleado){
        if (empleado==null){
            return null;
        }
        return new EmpleadoEntidad(
                empleado.getId_empleado(),
                empleado.getDni(),
                empleado.getNombre(),
                empleado.getApellidos(),
                empleado.getFecha_nacimiento(),
                empleado.getGenero(),
                empleado.getTelefono(),
                empleado.getEstado_civil(),
                empleado.getDireccion(),
                empleado.getEstado(),
                null,
                null,
                null
        );
    }

    public Empleado toDomainModel(){

        return new Empleado(id_empleado,dni,nombre,apellidos,fecha_nacimiento,genero,telefono,estado_civil,direccion,estado);

    }

}
