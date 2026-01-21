package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Repository.on.JpaRamaRepositorioAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class RamaServicio implements ICrearCasoUso<Rama>, ILeerCasoUso<Rama>, IModificarCasoUso<Rama>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Rama> leerRama;
    private final ICrearCasoUso<Rama> crearRama;
    private final IModificarCasoUso<Rama> modificarRama;
    private final IEliminarCasoUso<Integer> eliminarRamaPorId;
    private final JpaRamaRepositorioAdapter ramaRepositorioPort;


    @Override
    public Rama crearObjeto(Rama rama) {
        Optional<Rama> existingRama = ramaRepositorioPort.buscarPorNombre(rama.getNombre());
        if (existingRama.isPresent()) {
            throw new RuntimeException("La rama con el nombre '" + rama.getNombre() + "' ya existe.");
        }
        return crearRama.crearObjeto(rama);
    }

    @Override
    public Integer eliminarObjeto(Integer idRama) {
        return eliminarRamaPorId.eliminarObjeto(idRama);
    }

    @Override
    public Optional<Rama> leerObjeto(Integer id) {
        return leerRama.leerObjeto(id);
    }

    @Override
    public List<Rama> leerObjetos() {
        return leerRama.leerObjetos();
    }

    @Override
    public Optional<Rama> modificarObjeto(Integer id, Rama rama) {
        Optional<Rama> existingRama = ramaRepositorioPort.buscarPorNombre(rama.getNombre());
        if (existingRama.isPresent() && !existingRama.get().getId_rama().equals(id)) {
            throw new RuntimeException("El nombre de rama '" + rama.getNombre() + "' ya está en uso por otra rama.");
        }
        return modificarRama.modificarObjeto(id, rama);
    }

    public void desactivarRama(Integer idRama) {
        Optional<Rama> ramaOpt = leerRama.leerObjeto(idRama);
        if (ramaOpt.isPresent()) {
            Rama rama = ramaOpt.get();
            rama.setEstado(false);
            modificarRama.modificarObjeto(idRama, rama);
        } else {
            throw new RuntimeException("Rama con ID " + idRama + " no encontrada para desactivar.");
        }
    }

    public void activarRama(Integer idRama) {
        Optional<Rama> ramaOpt = leerRama.leerObjeto(idRama);
        if (ramaOpt.isPresent()) {
            Rama rama = ramaOpt.get();
            rama.setEstado(true);
            modificarRama.modificarObjeto(idRama, rama);
        } else {
            throw new RuntimeException("Rama con ID " + idRama + " no encontrada para activar.");
        }
    }

    public Page<Rama> leerRamasPaginadas(
            String searchTerm,
            Boolean estado,
            Integer idProfesion,
            Pageable pageable) {
        return ramaRepositorioPort.buscarRamasPaginadas(searchTerm, estado, idProfesion, pageable);
    }
}