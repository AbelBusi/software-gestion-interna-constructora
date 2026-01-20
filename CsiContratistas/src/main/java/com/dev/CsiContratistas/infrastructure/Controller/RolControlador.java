package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.RolServicio;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.infrastructure.dto.Rol.CrearRolDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rol.EliminarRolDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rol.LeerRolDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rol.ModificarRolDTO;
import com.dev.CsiContratistas.infrastructure.mapper.RolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin ("http://localhost:3000/")
public class RolControlador {

    private final RolServicio rolServicio;

    @PostMapping
    public ResponseEntity<?> crearRol(@RequestBody CrearRolDTO crearRolDTO) {
        try {
            Rol rolDomain = RolMapper.crearDtoDomain(crearRolDTO);

            Rol crearRol = rolServicio.crearObjeto(rolDomain);

            return new ResponseEntity<>(crearRolDTO,HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerRolDTO> LeerRol(@PathVariable Integer id){

        Optional<Rol> rol = rolServicio.leerObjeto(id);
        LeerRolDTO leerRolDTO=RolMapper.leerDTORol(rol.orElse(null));
        return new ResponseEntity<>(leerRolDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarRol(@PathVariable Integer id, @RequestBody ModificarRolDTO modificarRolDTO) {
        try {
            if (rolServicio.existpornombre(modificarRolDTO.getNombre(), id)) {
                return new ResponseEntity<>("El nombre del rol ya existe", HttpStatus.BAD_REQUEST);
            }

            Rol rol = RolMapper.actualizarDtoDomain(modificarRolDTO);
            Optional<Rol> modificarRol = rolServicio.modificarObjeto(id, rol);

            if (modificarRol.isPresent()) {
                return new ResponseEntity<>("Rol actualizado correctamente ",HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Rol no encontrado", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarRolDTO> eliminarRol(@PathVariable Integer id){

        rolServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerRolDTO>> mostrarRoles(){

        List<Rol> roles= rolServicio.leerObjetos();

        List<LeerRolDTO> respuesta=roles.stream()
                .map(RolMapper::leerDTORol)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}