package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.MaterialEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaMaterialRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaMaterialRepositorioAdapter implements ObjectRepositorioPort<Material, Integer> {

    private final IJpaMaterialRepositorio iJpaMaterialRepositorio;

    public boolean existByNombre(String nombre) {
        return iJpaMaterialRepositorio.existsByNombre(nombre);
    }

    @Override
    public Material guardar(Material objeto) {

        MaterialEntidad materialEntidad = MaterialEntidad.fromDomainModel(objeto);
        MaterialEntidad guardarMaterialEntidad = iJpaMaterialRepositorio.save(materialEntidad);

        return guardarMaterialEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<MaterialEntidad> clienteMaterialOptional = iJpaMaterialRepositorio.findById(integer);

        if (!clienteMaterialOptional.isPresent()){
            return 0;
        }

        MaterialEntidad materialEntidad= clienteMaterialOptional.get();

        materialEntidad.setEstado(false);

        MaterialEntidad actualizarMaterialEntidad=iJpaMaterialRepositorio.save(materialEntidad);

        return 1;

    }

    @Override
    public Optional<Material> leer(Integer id) {
        return iJpaMaterialRepositorio.findById(id).map(MaterialEntidad::toDomainModel);
    }

    @Override
    public List<Material> leerObjetos() {
        return iJpaMaterialRepositorio.findAll().stream()
                .map(MaterialEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Material> modificar(Integer id, Material material) {

        Boolean idMaterial = iJpaMaterialRepositorio.existsById(id);

        if (!idMaterial) {
            return Optional.empty();
        }

        material.setId_material(id);
        MaterialEntidad materialEntidad = MaterialEntidad.fromDomainModel(material);
        MaterialEntidad actualizarMaterialEntidad = iJpaMaterialRepositorio.save(materialEntidad);

        return Optional.of(actualizarMaterialEntidad.toDomainModel());
    }

    public Page<Material> leerObjetosPaginados(Pageable pageable) {
        return iJpaMaterialRepositorio.findByEstadoTrue(pageable)
                .map(MaterialEntidad::toDomainModel);
    }
    public long contarMaterialesActivos() {
        return iJpaMaterialRepositorio.countByEstadoTrue();
    }

    public List<Material> obtenerUltimosMaterialesRegistrados() {
        return iJpaMaterialRepositorio.findTop5ByEstadoTrueOrderByIdDesc()
                .stream()
                .map(MaterialEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

}
