package com.dev.CsiContratistas.application.CasoUso.Rama;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarRamaCasoUsoImpl implements IModificarCasoUso<Rama> {

    private final ObjectRepositorioPort<Rama,Integer> ramaRepositorioPort;

    @Override
    public Optional<Rama> modificarObjeto(Integer id,Rama rama) {
        return ramaRepositorioPort.modificar(id,rama);
    }

}