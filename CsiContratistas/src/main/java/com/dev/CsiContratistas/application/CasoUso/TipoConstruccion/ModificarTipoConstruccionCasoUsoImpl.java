package com.dev.CsiContratistas.application.CasoUso.TipoConstruccion;

import com.dev.CsiContratistas.domain.model.Tipo_construccion;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarTipoConstruccionCasoUsoImpl implements IModificarCasoUso<Tipo_construccion> {

    private final ObjectRepositorioPort<Tipo_construccion,Integer> tipoConstruccionIntegerObjectRepositorioPort;

    @Override
    public Optional<Tipo_construccion> modificarObjeto(Integer id,Tipo_construccion tipoConstruccion) {
        return tipoConstruccionIntegerObjectRepositorioPort.modificar(id,tipoConstruccion);
    }

}