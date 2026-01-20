package com.dev.CsiContratistas.application.CasoUso.TipoConstruccion;

import com.dev.CsiContratistas.domain.model.Tipo_construccion;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerTipoConstruccionCasoUsoImpl implements ILeerCasoUso<Tipo_construccion> {

    private final ObjectRepositorioPort<Tipo_construccion,Integer> tipoConstruccionIntegerObjectRepositorioPort;


    @Override
    public Optional<Tipo_construccion> leerObjeto(Integer id) {
        return tipoConstruccionIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Tipo_construccion> leerObjetos() {
        return tipoConstruccionIntegerObjectRepositorioPort.leerObjetos();
    }
}