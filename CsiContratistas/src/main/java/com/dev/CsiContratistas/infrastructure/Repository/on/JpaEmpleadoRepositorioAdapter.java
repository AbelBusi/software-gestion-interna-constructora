package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.domain.ports.out.ObjectValidRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.EmpleadoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.FormaTerrenoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.MaterialEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.ProfesionEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaEmpleadoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaEmpleadoRepositorioAdapter implements ObjectRepositorioPort<Empleado,Integer>, ObjectValidRepositorioPort<String> {

    private final IJpaEmpleadoRepositorio jpaEmpleadoRepositorio;

    @Override
    public Empleado guardar(Empleado objeto) {

        EmpleadoEntidad empleadoEntidad = EmpleadoEntidad.fromDomainModel(objeto);
        EmpleadoEntidad guardarEmpleadoEntidad = jpaEmpleadoRepositorio.save(empleadoEntidad);

        return guardarEmpleadoEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<EmpleadoEntidad> formaEmpleadoEntidad = jpaEmpleadoRepositorio.findById(integer);
        if (!formaEmpleadoEntidad.isPresent()){
            return 0;
        }
        EmpleadoEntidad empleadoEntidad = formaEmpleadoEntidad.get();
        empleadoEntidad.setEstado("Inactivo");
        jpaEmpleadoRepositorio.save(empleadoEntidad);
        return 1;

    }

    @Override
    public Optional<Empleado> leer(Integer id) {
        return jpaEmpleadoRepositorio.findById(id).map(EmpleadoEntidad::toDomainModel);
    }

    @Override
    public List<Empleado> leerObjetos() {
        return jpaEmpleadoRepositorio.findAll().stream()
                .map(EmpleadoEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Empleado> modificar(Integer id,Empleado empleado) {

        return jpaEmpleadoRepositorio.findById(id).map(existingEntidad -> {
            existingEntidad.setDni(empleado.getDni());
            existingEntidad.setNombre(empleado.getNombre());
            existingEntidad.setApellidos(empleado.getApellidos());
            existingEntidad.setFecha_nacimiento(empleado.getFecha_nacimiento());
            existingEntidad.setGenero(empleado.getGenero());
            existingEntidad.setTelefono(empleado.getTelefono());
            existingEntidad.setEstado_civil(empleado.getEstado_civil());
            existingEntidad.setDireccion(empleado.getDireccion());
            existingEntidad.setEstado(empleado.getEstado());
            EmpleadoEntidad actualizarEmpleadoEntidad = jpaEmpleadoRepositorio.save(existingEntidad);
            return actualizarEmpleadoEntidad.toDomainModel();
        });
    }
    public boolean existeDni(String dni) {
        return jpaEmpleadoRepositorio.existsByDni(dni);

    }

    @Override
    public boolean leerParametro(String dni) {

        boolean empleadoExistenciaDni= jpaEmpleadoRepositorio.existsByDni(dni);

        if(!empleadoExistenciaDni){
            return false;
        }

        return true;
    }
    public Page<Empleado> leerObjetosPaginados(Pageable pageable) {
        return jpaEmpleadoRepositorio.listarOrdenadoPorId(pageable)
                .map(EmpleadoEntidad::toDomainModel);
    }

    public long contarEmpleadosActivos() {
        return jpaEmpleadoRepositorio.countByEstado("Activo");
    }

    public List<Empleado> listarUltimosEmpleadosRegistrados() {
        return jpaEmpleadoRepositorio.findTop5ByEstadoTrueOrderById_empleadoDesc().stream()
                .map(EmpleadoEntidad::toDomainModel)
                .collect(Collectors.toList());
    }
}
