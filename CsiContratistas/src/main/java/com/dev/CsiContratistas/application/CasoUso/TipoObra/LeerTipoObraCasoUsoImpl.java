package com.dev.CsiContratistas.application.CasoUso.TipoObra;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerTipoObraCasoUsoImpl implements ILeerCasoUso<Tipo_obra> {

    private final ObjectRepositorioPort<Tipo_obra,Integer> TipoObraIntegerObjectRepositorioPort;


    @Override
    public Optional<Tipo_obra> leerObjeto(Integer id) {
        return TipoObraIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Tipo_obra> leerObjetos() {
        return TipoObraIntegerObjectRepositorioPort.leerObjetos();
    }
}