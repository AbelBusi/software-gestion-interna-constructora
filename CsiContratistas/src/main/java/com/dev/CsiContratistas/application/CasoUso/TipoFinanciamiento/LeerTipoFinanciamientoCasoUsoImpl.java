package com.dev.CsiContratistas.application.CasoUso.TipoFinanciamiento;

import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerTipoFinanciamientoCasoUsoImpl implements ILeerCasoUso<Tipo_financiamiento> {

    private final ObjectRepositorioPort<Tipo_financiamiento,Integer> tipoFinanciamientoIntegerObjectRepositorioPort;


    @Override
    public Optional<Tipo_financiamiento> leerObjeto(Integer id) {
        return tipoFinanciamientoIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Tipo_financiamiento> leerObjetos() {
        return tipoFinanciamientoIntegerObjectRepositorioPort.leerObjetos();
    }
}