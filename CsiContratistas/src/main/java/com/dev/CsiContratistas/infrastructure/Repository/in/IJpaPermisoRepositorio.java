package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.PermisoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaPermisoRepositorio extends JpaRepository<PermisoEntidad,Integer> {
}
