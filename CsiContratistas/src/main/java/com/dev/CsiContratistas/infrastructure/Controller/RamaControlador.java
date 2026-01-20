package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.ProfesionServicio;
import com.dev.CsiContratistas.application.Services.RamaServicio;
import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.CrearPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.EliminarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.LeerPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.ModificarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.CrearRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.LeerRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.ModificarRamaDTO;
import com.dev.CsiContratistas.infrastructure.mapper.PermisoMapper;
import com.dev.CsiContratistas.infrastructure.mapper.RamaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ramas")
@CrossOrigin ("http://localhost:3000/")
public class RamaControlador {

    private final RamaServicio ramaServicio;
    private final ProfesionServicio profesionServicio;

    @PostMapping
    public ResponseEntity<?> crearRama(@RequestBody CrearRamaDTO crearRamaDTO) {
        try {

            Profesiones profesiones = new Profesiones();
            profesiones.setId_profesion(crearRamaDTO.getId_profesion());
            Rama ramaDomain = RamaMapper.crearDtoDomain(crearRamaDTO, profesiones);
            Rama crearRama = ramaServicio.crearObjeto(ramaDomain);

            return new ResponseEntity<>(crearRamaDTO, HttpStatus.CREATED);

        } catch (RuntimeException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<LeerRamaDTO> LeerRama(@PathVariable Integer id){

        Optional<Rama> rama = ramaServicio.leerObjeto(id);
        LeerRamaDTO leerRamaDTO=RamaMapper.leerDTORama(rama.orElse(null));
        return new ResponseEntity<>(leerRamaDTO,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ModificarRamaDTO> modificarRama(@PathVariable Integer id, @RequestBody ModificarRamaDTO modificarRamaDTO) {

        Profesiones profesiones = new Profesiones();
        profesiones.setId_profesion(modificarRamaDTO.getId_profesion());
        Rama rama = RamaMapper.actualizarDtoDomain(modificarRamaDTO,profesiones);

        Optional<Rama> modificarRama = ramaServicio.modificarObjeto(id,rama);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarPermisoDTO> eliminarRama(@PathVariable Integer id){

        ramaServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }


    @GetMapping
    public ResponseEntity<List<LeerRamaDTO>> mostrarRamas(){

        List<Rama> ramas= ramaServicio.leerObjetos();

        List<LeerRamaDTO> respuesta=ramas.stream()
                .map(RamaMapper::leerDTORama)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }
}