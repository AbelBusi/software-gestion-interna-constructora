package com.dev.CsiContratistas.application.CasoUso.TipoSuelo;

import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerTipoSueloCasoUsoImpl implements ILeerCasoUso<Tipo_suelo> {

    private final ObjectRepositorioPort<Tipo_suelo,Integer> TipoSueloIntegerObjectRepositorioPort;

    @Override
    public Optional<Tipo_suelo> leerObjeto(Integer id) {
        return TipoSueloIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Tipo_suelo> leerObjetos() {
        return TipoSueloIntegerObjectRepositorioPort.leerObjetos();
    }
}