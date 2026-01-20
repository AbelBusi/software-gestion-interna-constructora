package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.PermisoServicio;
import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.CrearPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.EliminarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.LeerPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.ModificarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rol.CrearRolDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rol.EliminarRolDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rol.LeerRolDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rol.ModificarRolDTO;
import com.dev.CsiContratistas.infrastructure.mapper.PermisoMapper;
import com.dev.CsiContratistas.infrastructure.mapper.RolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/permisos")
@CrossOrigin ("http://localhost:3000/")
public class PermisoControlador {

    private final PermisoServicio permisoServicio;

    @PostMapping
    public ResponseEntity<CrearPermisoDTO> crearPermiso(@RequestBody CrearPermisoDTO crearPermisoDTO) {

        Permiso permisoDomain = PermisoMapper.crearDtoDomain(crearPermisoDTO);

        Permiso crearRol = permisoServicio.crearObjeto(permisoDomain);

        return new ResponseEntity<>(crearPermisoDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerPermisoDTO> LeerPermiso(@PathVariable Integer id){

        Optional<Permiso> permiso = permisoServicio.leerObjeto(id);
        LeerPermisoDTO leerRolDTO=PermisoMapper.leerDTOPermiso(permiso.orElse(null));
        return new ResponseEntity<>(leerRolDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarPermisoDTO> modificarPermiso(@PathVariable Integer id, @RequestBody ModificarPermisoDTO modificarPermisoDTO) {

        Permiso permiso = PermisoMapper.actualizarDtoDomain(modificarPermisoDTO);

        Optional<Permiso> modificarPermiso = permisoServicio.modificarObjeto(id,permiso);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarPermisoDTO> eliminarRol(@PathVariable Integer id){

        permisoServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerPermisoDTO>> mostrarRoles(){

        List<Permiso> roles= permisoServicio.leerObjetos();

        List<LeerPermisoDTO> respuesta=roles.stream()
                .map(PermisoMapper::leerDTOPermiso)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}