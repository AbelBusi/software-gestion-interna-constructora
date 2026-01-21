package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.ports.in.*;
import com.dev.CsiContratistas.infrastructure.Repository.on.JpaEmpleadoRepositorioAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EmpleadoServicio implements ICrearCasoUso<Empleado>, ILeerCasoUso<Empleado>, IModificarCasoUso<Empleado>,
        IEliminarCasoUso<Integer>, IVerificarObjetoCasoUso<String> {

    private final ILeerCasoUso<Empleado> leerEmpleado;
    private final ICrearCasoUso<Empleado> crearEmpleado;
    private final IModificarCasoUso<Empleado> modificarEmpleado;
    private final IEliminarCasoUso<Integer> eliminarEmpleadoPorId;
    private final IVerificarObjetoCasoUso<String> verificarDniEmpleado;

    private final JpaEmpleadoRepositorioAdapter jpaEmpleadoRepositorioAdapter;

    @Override
    public Empleado crearObjeto(Empleado empleado) {
        return crearEmpleado.crearObjeto(empleado);
    }

    @Override
    public Integer eliminarObjeto(Integer idEmpleado) {
        return eliminarEmpleadoPorId.eliminarObjeto(idEmpleado);
    }

    @Override
    public Optional<Empleado> leerObjeto(Integer id) {
        return leerEmpleado.leerObjeto(id);
    }

    @Override
    public List<Empleado> leerObjetos() {
        return leerEmpleado.leerObjetos();
    }

    @Override
    public Optional<Empleado> modificarObjeto(Integer id, Empleado empleado) {
        return modificarEmpleado.modificarObjeto(id, empleado);
    }

    public boolean existeDni(String dni) {
        return jpaEmpleadoRepositorioAdapter.existeDni(dni);
    }

    @Override
    public boolean obtenerPorParametro(String parametro) {
        return verificarDniEmpleado.obtenerPorParametro(parametro);
    }

    public Page<Empleado> leerObjetosPaginados(Pageable pageable) {
        return jpaEmpleadoRepositorioAdapter.leerObjetosPaginados(pageable);
    }
    public long contarEmpleadosActivos() {
        return jpaEmpleadoRepositorioAdapter.contarEmpleadosActivos();
    }

    public List<Empleado> leerUltimosEmpleados(int cantidad) {
        List<Empleado> ultimosEmpleados = jpaEmpleadoRepositorioAdapter.listarUltimosEmpleadosRegistrados();
        return ultimosEmpleados.stream().limit(cantidad).toList();
    }
}