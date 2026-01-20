package com.dev.CsiContratistas.application.CasoUso.TipoSuelo;

import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarTipoSueloCasoUsoImpl implements IModificarCasoUso<Tipo_suelo> {

    private final ObjectRepositorioPort<Tipo_suelo,Integer> tipoSueloIntegerObjectRepositorioPort;

    @Override
    public Optional<Tipo_suelo> modificarObjeto(Integer id,Tipo_suelo tipoSuelo) {
        return tipoSueloIntegerObjectRepositorioPort.modificar(id,tipoSuelo);
    }

}