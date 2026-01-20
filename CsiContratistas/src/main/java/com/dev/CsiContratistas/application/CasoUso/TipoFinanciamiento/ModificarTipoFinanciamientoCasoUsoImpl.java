package com.dev.CsiContratistas.application.CasoUso.TipoFinanciamiento;

import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarTipoFinanciamientoCasoUsoImpl implements IModificarCasoUso<Tipo_financiamiento> {

    private final ObjectRepositorioPort<Tipo_financiamiento,Integer> tipoFinanciamientoIntegerObjectRepositorioPort;

    @Override
    public Optional<Tipo_financiamiento> modificarObjeto(Integer id,Tipo_financiamiento tipoFinanciamiento) {
        return tipoFinanciamientoIntegerObjectRepositorioPort.modificar(id,tipoFinanciamiento);
    }

}