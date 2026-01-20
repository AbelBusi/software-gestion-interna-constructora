package com.dev.CsiContratistas.application.CasoUso.Rol;

import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarRolCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Rol,Integer> rolRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idRol) {
        return rolRepositorioPort.eliminar(idRol);
    }
}