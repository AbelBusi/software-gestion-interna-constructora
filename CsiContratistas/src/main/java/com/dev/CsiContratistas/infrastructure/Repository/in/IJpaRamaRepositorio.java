package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.RamaEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IJpaRamaRepositorio extends JpaRepository<RamaEntidad,Integer> {
    public Optional <RamaEntidad> findByNombre(String nombre);

    @Query("SELECT r FROM RamaEntidad r WHERE " +
            "(:searchTerm IS NULL OR LOWER(r.nombre) LIKE %:searchTerm% OR LOWER(r.descripcion) LIKE %:searchTerm%) AND " +
            "(:estado IS NULL OR r.estado = :estado) AND " +
            "(:idProfesion IS NULL OR r.id_profesion.id_profesion = :idProfesion)")
    Page<RamaEntidad> findByFilters(
            @Param("searchTerm") String searchTerm,
            @Param("estado") Boolean estado,
            @Param("idProfesion") Integer idProfesion,
            Pageable pageable);
}
