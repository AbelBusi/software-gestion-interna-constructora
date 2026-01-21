package com.dev.CsiContratistas.application.CasoUso.Rol;

import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarRolCasoUsoImpl implements IModificarCasoUso<Rol> {

    private final ObjectRepositorioPort<Rol,Integer> tipoMaterialRepositorioPort;

    @Override
    public Optional<Rol> modificarObjeto(Integer id,Rol rol) {
        return tipoMaterialRepositorioPort.modificar(id,rol);
    }

}