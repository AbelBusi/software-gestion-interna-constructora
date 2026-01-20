package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.ProfesionEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaProfesionRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaProfesionRepositorioAdapter implements ObjectRepositorioPort<Profesiones,Integer> {

    private final IJpaProfesionRepositorio jpaProfesionRepositorio;

    @Override
    public Profesiones guardar(Profesiones objeto) {
        ProfesionEntidad profesionEntidad = ProfesionEntidad.fromDomainModel(objeto);
        ProfesionEntidad guardarProfesionEntidad = jpaProfesionRepositorio.save(profesionEntidad);
        return guardarProfesionEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<ProfesionEntidad> clienteEntidadOptional = jpaProfesionRepositorio.findById(integer);
        if (!clienteEntidadOptional.isPresent()){
            return 0;
        }
        ProfesionEntidad clienteEntidad= clienteEntidadOptional.get();
        clienteEntidad.setEstado(false);
        ProfesionEntidad actualizarClienteEntidad=jpaProfesionRepositorio.save(clienteEntidad);
        return 1;

    }

    @Override
    public Optional<Profesiones> leer(Integer id) {
        return jpaProfesionRepositorio.findById(id).map(ProfesionEntidad::toDomainModel);
    }

    @Override
    public List<Profesiones> leerObjetos() {
        return jpaProfesionRepositorio.findAll().stream()
                .map(ProfesionEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Profesiones> modificar(Integer id,Profesiones profesiones) {
        Boolean idProfesion = jpaProfesionRepositorio.existsById(id);
        if(!idProfesion){
            return Optional.empty();
        }
        profesiones.setId_profesion(id);
        ProfesionEntidad profesionEntidad = ProfesionEntidad.fromDomainModel(profesiones);
        ProfesionEntidad actualizarProfesionEntidad=jpaProfesionRepositorio.save(profesionEntidad);
        return  Optional.of(actualizarProfesionEntidad.toDomainModel());
    }
    public Optional<Profesiones> buscarProfesionpornombre(String nombre) {
        Optional<ProfesionEntidad> profesionEntidad = jpaProfesionRepositorio.findByNombre(nombre);
        return profesionEntidad.map(ProfesionEntidad::toDomainModel);
    }

    public Page<Profesiones> leerObjetosPaginados(Pageable pageable) {
        return jpaProfesionRepositorio.listarOrdenadoPorId(pageable)
                .map(ProfesionEntidad::toDomainModel);
    }

}