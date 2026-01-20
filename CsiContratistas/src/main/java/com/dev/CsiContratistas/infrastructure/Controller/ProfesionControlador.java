package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.PermisoServicio;
import com.dev.CsiContratistas.application.Services.ProfesionServicio;
import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.CrearPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.EliminarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.LeerPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.ModificarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Profesion.CrearProfesionDTO;
import com.dev.CsiContratistas.infrastructure.dto.Profesion.EliminarProfesionDTO;
import com.dev.CsiContratistas.infrastructure.dto.Profesion.LeerProfesionDTO;
import com.dev.CsiContratistas.infrastructure.dto.Profesion.ModificarProfesionDTO;
import com.dev.CsiContratistas.infrastructure.mapper.PermisoMapper;
import com.dev.CsiContratistas.infrastructure.mapper.ProfesionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profesiones")
@CrossOrigin("http://localhost:3000/")
public class ProfesionControlador {

    private final ProfesionServicio profesionServicio;

    @PostMapping
    public ResponseEntity<?> crearProfesion(@RequestBody CrearProfesionDTO crearProfesionDTO) {
        try {
            Profesiones profesionDomain = ProfesionMapper.crearDtoDomain(crearProfesionDTO);

            Profesiones crearRol = profesionServicio.crearObjeto(profesionDomain);

            return new ResponseEntity<>(crearProfesionDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerProfesionDTO> LeerPermiso(@PathVariable Integer id){

        Optional<Profesiones> profesion = profesionServicio.leerObjeto(id);
        LeerProfesionDTO leerProfesionDTO=ProfesionMapper.leerDTOProfesion(profesion.orElse(null));
        return new ResponseEntity<>(leerProfesionDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarProfesion(@PathVariable Integer id, @RequestBody ModificarProfesionDTO modificarProfesionDTO) {
        try{
            if(profesionServicio.existpornombre(modificarProfesionDTO.getNombre() ,id)){
                return new ResponseEntity<>("La profesion ya existe",HttpStatus.BAD_REQUEST);
            }
            Profesiones profesiones = ProfesionMapper.actualizarDtoDomain(modificarProfesionDTO);

            Optional<Profesiones> modificarProfesion = profesionServicio.modificarObjeto(id,profesiones);
            if(modificarProfesion.isPresent()){
                return new ResponseEntity<>("Profesion actualizada correctamente",HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Profesion no encontrada para actualizar",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarProfesionDTO> eliminarProfesion(@PathVariable Integer id){

        profesionServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerProfesionDTO>> mostrarRoles(){

        List<Profesiones> profesiones= profesionServicio.leerObjetos();

        List<LeerProfesionDTO> respuesta=profesiones.stream()
                .map(ProfesionMapper::leerDTOProfesion)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}