package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.FormaTerrenoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoSueloEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaFormaTerrenoRepositorio extends JpaRepository<FormaTerrenoEntidad,Integer> {
}
