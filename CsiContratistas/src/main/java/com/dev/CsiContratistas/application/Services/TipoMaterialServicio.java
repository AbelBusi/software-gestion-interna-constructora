package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.infrastructure.Repository.on.JpaTipoMaterialRepositorioAdapter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TipoMaterialServicio implements ICrearCasoUso<Tipo_material>, ILeerCasoUso<Tipo_material>, IModificarCasoUso<Tipo_material>, IEliminarCasoUso<Integer>{

    private final ILeerCasoUso<Tipo_material> leerTipoMaterial;

    private final ICrearCasoUso<Tipo_material> crearTipoMaterial;

    private final IModificarCasoUso<Tipo_material> modificarTipoMaterial;

    private final IEliminarCasoUso<Integer> eliminarTipoMaterialPorId;

    private final JpaTipoMaterialRepositorioAdapter jpaTipoMaterialRepositorio;


    public boolean tipoMaterialExiste(String nombre) {
        return jpaTipoMaterialRepositorio.existByNombre(nombre);
    }
    public void eliminarTipoMaterialLogicamente(Integer tipoMaterialId) {
        List<Material> materialesasociados =jpaTipoMaterialRepositorio.obtenerMaterialesPorTipo(tipoMaterialId);
        if(!materialesasociados.isEmpty()){
            throw new RuntimeException("Existen materiales asociados a este tipo.");
        }
        jpaTipoMaterialRepositorio.marcarComoInactivo(tipoMaterialId);
    }

    @Override
    public Tipo_material crearObjeto(Tipo_material Tipomaterial) {
        return crearTipoMaterial.crearObjeto(Tipomaterial);
    }

    @Override
    public Integer eliminarObjeto(Integer idTipoMaterial) {
        return eliminarTipoMaterialPorId.eliminarObjeto(idTipoMaterial);
    }

    @Override
    public Optional<Tipo_material> leerObjeto(Integer id) {
        return leerTipoMaterial.leerObjeto(id);
    }

    @Override
    public List<Tipo_material> leerObjetos() {
        return leerTipoMaterial.leerObjetos();
    }

    @Override
    public Optional<Tipo_material> modificarObjeto(Integer id,Tipo_material tipoMaterial) {
        return modificarTipoMaterial.modificarObjeto(id,tipoMaterial);
    }
}