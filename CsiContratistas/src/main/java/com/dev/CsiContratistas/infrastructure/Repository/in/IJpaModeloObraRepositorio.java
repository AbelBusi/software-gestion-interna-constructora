package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.CargoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.ModeloObraEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaModeloObraRepositorio extends JpaRepository<ModeloObraEntidad,Integer> {
}
