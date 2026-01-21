package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.infrastructure.Repository.on.JpaProfesionRepositorioAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProfesionServicio implements ICrearCasoUso<Profesiones>, ILeerCasoUso<Profesiones>, IModificarCasoUso<Profesiones>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Profesiones> leerProfesion;
    private final ICrearCasoUso<Profesiones> crearProfesion;
    private final IModificarCasoUso<Profesiones> modificarProfesion;
    private final IEliminarCasoUso<Integer> eliminarProfesionPorId;
    private final JpaProfesionRepositorioAdapter jpaProfesionRepositorio;

    @Override
    public Profesiones crearObjeto(Profesiones profesiones) {
        Optional<Profesiones> existprofesion = jpaProfesionRepositorio.buscarProfesionpornombre(profesiones.getNombre());
        if (existprofesion.isPresent()) {
            throw new RuntimeException("La profesion con el nombre " + profesiones.getNombre() + " ya esta registrado");
        }
        return crearProfesion.crearObjeto(profesiones);
    }

    public boolean existpornombre(String nombre, Integer idProfesion) {
        Optional<Profesiones> profesionexiste = jpaProfesionRepositorio.buscarProfesionpornombre(nombre);
        return profesionexiste.isPresent() && !profesionexiste.get().getId_profesion().equals(idProfesion);
    }

    @Override
    public Integer eliminarObjeto(Integer idProfesion) {
        return eliminarProfesionPorId.eliminarObjeto(idProfesion);
    }

    @Override
    public Optional<Profesiones> leerObjeto(Integer id) {
        return leerProfesion.leerObjeto(id);
    }

    @Override
    public List<Profesiones> leerObjetos() {
        return leerProfesion.leerObjetos();
    }

    @Override
    public Optional<Profesiones> modificarObjeto(Integer id, Profesiones profesiones) {
        return modificarProfesion.modificarObjeto(id, profesiones);
    }

    public void desactivarProfesion(Integer idProfesion) {
        Optional<Profesiones> profesionOpt = leerProfesion.leerObjeto(idProfesion);
        if (profesionOpt.isPresent()) {
            Profesiones profesion = profesionOpt.get();
            profesion.setEstado(false);
            modificarProfesion.modificarObjeto(idProfesion, profesion);
        } else {
            throw new RuntimeException("Profesión con ID " + idProfesion + " no encontrada para desactivar.");
        }
    }

    public void activarProfesion(Integer idProfesion) {
        Optional<Profesiones> profesionOpt = leerProfesion.leerObjeto(idProfesion);
        if (profesionOpt.isPresent()) {
            Profesiones profesion = profesionOpt.get();
            profesion.setEstado(true);
            modificarProfesion.modificarObjeto(idProfesion, profesion);
        } else {
            throw new RuntimeException("Profesión con ID " + idProfesion + " no encontrada para activar.");
        }
    }

    public Page<Profesiones> leerObjetosPaginados(Pageable pageable) {
        return jpaProfesionRepositorio.leerObjetosPaginados(pageable);
    }
}