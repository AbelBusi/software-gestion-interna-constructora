package com.dev.CsiContratistas.application.CasoUso.Profesion;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarProfesionCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Profesiones,Integer> profesionRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idProfesion) {
        return profesionRepositorioPort.eliminar(idProfesion);
    }
}