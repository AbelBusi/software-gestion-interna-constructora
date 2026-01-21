package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.infrastructure.Repository.on.JpaMaterialRepositorioAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MaterialServicio implements ICrearCasoUso<Material>, ILeerCasoUso<Material>, IModificarCasoUso<Material>,
        IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Material> leerMaterial;

    private final ICrearCasoUso<Material> crearMaterial;

    private final IModificarCasoUso<Material> modificarMaterial;

    private final IEliminarCasoUso<Integer> eliminarMaterialPorId;

    private final JpaMaterialRepositorioAdapter jpaMaterialRepositorioAdapter;

    public boolean materialExiste(String nombre) {
        return jpaMaterialRepositorioAdapter.existByNombre(nombre);
    }
    @Override
    public Material crearObjeto(Material material) {
        return crearMaterial.crearObjeto(material);
    }

    @Override
    public Integer eliminarObjeto(Integer idMaterial) {
        return eliminarMaterialPorId.eliminarObjeto(idMaterial);
    }

    @Override
    public Optional<Material> leerObjeto(Integer id) {
        return leerMaterial.leerObjeto(id);
    }

    @Override
    public List<Material> leerObjetos() {
        return leerMaterial.leerObjetos();
    }

    @Override
    public Optional<Material> modificarObjeto(Integer id, Material material) {
        return modificarMaterial.modificarObjeto(id, material);
    }

    public Page<Material> leerObjetosPaginados(Pageable pageable) {
        return jpaMaterialRepositorioAdapter.leerObjetosPaginados(pageable);
    }
    public long contarMaterialesActivos() {
        return jpaMaterialRepositorioAdapter.contarMaterialesActivos();
    }

    public List<Material> leerUltimos(int cantidad) {
        List<Material> materiales = jpaMaterialRepositorioAdapter.obtenerUltimosMaterialesRegistrados();
        return materiales.stream().limit(cantidad).toList();
    }

}