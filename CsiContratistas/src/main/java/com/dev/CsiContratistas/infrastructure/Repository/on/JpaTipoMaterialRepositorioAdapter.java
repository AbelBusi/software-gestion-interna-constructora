package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.MaterialEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoMaterialEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaMaterialRepositorio;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaTipoMaterialRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaTipoMaterialRepositorioAdapter implements ObjectRepositorioPort<Tipo_material,Integer> {

    private final IJpaTipoMaterialRepositorio JpaTipoMaterialRepositorio;
    private final IJpaMaterialRepositorio iJpaMaterialRepositorio;

    public boolean existByNombre(String nombre) {
        return JpaTipoMaterialRepositorio.existsByNombre(nombre);
    }
    public void marcarComoInactivo(Integer tipoMaterialId) {
        TipoMaterialEntidad tipoMaterialEntidad = JpaTipoMaterialRepositorio.findById(tipoMaterialId)
                .orElseThrow(() -> new RuntimeException("Tipo de material no encontrado"));

        tipoMaterialEntidad.setEstado(false);
        JpaTipoMaterialRepositorio.save(tipoMaterialEntidad);
    }
    public List<Material> obtenerMaterialesPorTipo(Integer idTipomaterial) {
        return iJpaMaterialRepositorio.findByTipoMaterial_idTipomaterial(idTipomaterial).stream()
                .map(MaterialEntidad::toDomainModel)
                .collect(Collectors.toList());
    }
    @Override
    public Tipo_material guardar(Tipo_material objeto) {

        TipoMaterialEntidad tipoMaterialEntidad = TipoMaterialEntidad.fromDomainModel(objeto);
        TipoMaterialEntidad guardarTipoMaterialEntidad = JpaTipoMaterialRepositorio.save(tipoMaterialEntidad);

        return guardarTipoMaterialEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<TipoMaterialEntidad> clienteEntidadOptional = JpaTipoMaterialRepositorio.findById(integer);

        if (!clienteEntidadOptional.isPresent()){
            return 0;
        }

        TipoMaterialEntidad clienteEntidad= clienteEntidadOptional.get();

        clienteEntidad.setEstado(false);

        TipoMaterialEntidad actualizarClienteEntidad=JpaTipoMaterialRepositorio.save(clienteEntidad);

        return 1;

    }

    @Override
    public Optional<Tipo_material> leer(Integer id) {
        return JpaTipoMaterialRepositorio.findById(id).map(TipoMaterialEntidad::toDomainModel);
    }

    @Override
    public List<Tipo_material> leerObjetos() {
        return JpaTipoMaterialRepositorio.findAll().stream()
                .map(TipoMaterialEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Tipo_material> modificar(Integer id,Tipo_material material) {

        Boolean idTipoMaterial = JpaTipoMaterialRepositorio.existsById(id);

        if(!idTipoMaterial){
            return Optional.empty();
        }

        material.setIdtipomaterial(id);
        TipoMaterialEntidad tipoMaterialEntidad = TipoMaterialEntidad.fromDomainModel(material);
        TipoMaterialEntidad actualizarTipoMaterialEntidad=JpaTipoMaterialRepositorio.save(tipoMaterialEntidad);

        return  Optional.of(actualizarTipoMaterialEntidad.toDomainModel());
    }

}
