package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TipoObraServicio implements ICrearCasoUso<Tipo_obra>, ILeerCasoUso<Tipo_obra>, IModificarCasoUso<Tipo_obra>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Tipo_obra> leerTipoObra;

    private final ICrearCasoUso<Tipo_obra> crearTipoObra;

    private final IModificarCasoUso<Tipo_obra> modificarTipoObra;

    private final IEliminarCasoUso<Integer> eliminarTipoObraPorId;

    @Override
    public Tipo_obra crearObjeto(Tipo_obra material) {
        return crearTipoObra.crearObjeto(material);
    }

    @Override
    public Integer eliminarObjeto(Integer idMaterial) {
        return eliminarTipoObraPorId.eliminarObjeto(idMaterial);
    }

    @Override
    public Optional<Tipo_obra> leerObjeto(Integer id) {
        return leerTipoObra.leerObjeto(id);
    }

    @Override
    public List<Tipo_obra> leerObjetos() {
        return leerTipoObra.leerObjetos();
    }

    @Override
    public Optional<Tipo_obra> modificarObjeto(Integer id,Tipo_obra material) {
        return modificarTipoObra.modificarObjeto(id,material);
    }
}