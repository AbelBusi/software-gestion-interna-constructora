package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.CargoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.FinanciamientoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.ObraEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IJpaFinanciamientoRepositorio extends JpaRepository<FinanciamientoEntidad,Integer> {

    @Query("SELECT f FROM FinanciamientoEntidad f WHERE f.id_financiamiento_obra = :obra")
    Optional<FinanciamientoEntidad> buscarPorObra(@Param("obra") ObraEntidad obra);


}
