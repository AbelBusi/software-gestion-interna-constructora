package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RamaEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaRamaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaRamaRepositorioAdapter implements ObjectRepositorioPort<Rama,Integer> {

    private final IJpaRamaRepositorio jpaRamaRepositorio;

    @Override
    public Rama guardar(Rama objeto) {
        RamaEntidad ramaEntidad = RamaEntidad.fromDomainModel(objeto);
        RamaEntidad guardarRamaEntidad = jpaRamaRepositorio.save(ramaEntidad);
        return guardarRamaEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {
        Optional<RamaEntidad> ramaEntidadOptional = jpaRamaRepositorio.findById(integer);
        if (!ramaEntidadOptional.isPresent()){
            return 0;
        }
        RamaEntidad ramaEntidad = ramaEntidadOptional.get();
        ramaEntidad.setEstado(false);
        jpaRamaRepositorio.save(ramaEntidad);
        return 1;
    }

    @Override
    public Optional<Rama> leer(Integer id) {
        return jpaRamaRepositorio.findById(id).map(RamaEntidad::toDomainModel);
    }

    @Override
    public List<Rama> leerObjetos() {
        return jpaRamaRepositorio.findAll().stream()
                .map(RamaEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rama> modificar(Integer id,Rama rama) {
        Boolean idRama = jpaRamaRepositorio.existsById(id);
        if(!idRama){
            return Optional.empty();
        }
        rama.setId_rama(id);
        RamaEntidad ramaEntidad = RamaEntidad.fromDomainModel(rama);
        RamaEntidad actualizarRamaEntidad= jpaRamaRepositorio.save(ramaEntidad);
        return  Optional.of(actualizarRamaEntidad.toDomainModel());
    }

    public Optional<Rama> buscarPorNombre(String nombre) {

        Optional<RamaEntidad> ramaEntidadOpt = jpaRamaRepositorio.findByNombre(nombre);
        return ramaEntidadOpt.map(RamaEntidad::toDomainModel);
    }

    public Page<Rama> buscarRamasPaginadas(
            String searchTerm,
            Boolean estado,
            Integer idProfesion,
            Pageable pageable) {
        String processedSearchTerm = (searchTerm != null && !searchTerm.trim().isEmpty()) ? searchTerm.toLowerCase() : null;

        return jpaRamaRepositorio.findByFilters(processedSearchTerm, estado, idProfesion, pageable)
                .map(RamaEntidad::toDomainModel);
    }
}
