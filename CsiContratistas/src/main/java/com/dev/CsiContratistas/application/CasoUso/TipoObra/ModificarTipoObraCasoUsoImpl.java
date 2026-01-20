package com.dev.CsiContratistas.application.CasoUso.TipoObra;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarTipoObraCasoUsoImpl implements IModificarCasoUso<Tipo_obra> {

    private final ObjectRepositorioPort<Tipo_obra,Integer> tipoObraIntegerObjectRepositorioPort;

    @Override
    public Optional<Tipo_obra> modificarObjeto(Integer id,Tipo_obra tipoObra) {
        return tipoObraIntegerObjectRepositorioPort.modificar(id,tipoObra);
    }

}