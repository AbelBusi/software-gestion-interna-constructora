package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.ProfesionEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface IJpaProfesionRepositorio extends JpaRepository<ProfesionEntidad,Integer> {
    public Optional <ProfesionEntidad> findByNombre(String nombre);

    @Query("SELECT p FROM ProfesionEntidad p ORDER BY p.id_profesion ASC")
    Page<ProfesionEntidad> listarOrdenadoPorId(Pageable pageable);

}