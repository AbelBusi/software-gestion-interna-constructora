package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.MaterialEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoMaterialEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJpaTipoMaterialRepositorio extends JpaRepository<TipoMaterialEntidad,Integer> {
        boolean existsByNombre(String nombre);

}
