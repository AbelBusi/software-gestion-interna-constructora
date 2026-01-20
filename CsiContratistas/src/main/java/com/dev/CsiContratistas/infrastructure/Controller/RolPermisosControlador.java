package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.RolPermisoServicio;
import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.RolPermiso.CrearRolPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolPermiso.EliminarRolPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolPermiso.LeerRolPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolPermiso.ModificarRolPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.CrearRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.EliminarRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.LeerRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.ModificarRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.mapper.RolPermisoMapper;
import com.dev.CsiContratistas.infrastructure.mapper.RolUsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roles_permisos")
@CrossOrigin ("http://localhost:3000/")
public class RolPermisosControlador {

    private final RolPermisoServicio rolPermisoServicio;

    @PostMapping
    public ResponseEntity<CrearRolPermisoDTO> crearRolPermiso(@RequestBody CrearRolPermisoDTO crearRolPermisoDTO) {


        Permiso permiso= new Permiso();
        permiso.setId_permiso(crearRolPermisoDTO.getId_permiso());

        Rol rol= new Rol();
        rol.setId_rol(crearRolPermisoDTO.getId_rol());

        Rol_Permiso rolPermisoDomain = RolPermisoMapper.crearDtoDomain(crearRolPermisoDTO,permiso,rol);

        Rol_Permiso crearRolPermiso = rolPermisoServicio.crearObjeto(rolPermisoDomain);

        return new ResponseEntity<>(crearRolPermisoDTO,HttpStatus.CREATED);

    }


    @GetMapping("/{id}")
    public ResponseEntity<LeerRolPermisoDTO> LeerRolPermiso(@PathVariable Integer id){

        Optional<Rol_Permiso> rolPermiso = rolPermisoServicio.leerObjeto(id);
        LeerRolPermisoDTO leerRolPermisoDTO=RolPermisoMapper.leerDTORolPermiso(rolPermiso.orElse(null));
        return new ResponseEntity<>(leerRolPermisoDTO,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ModificarRolPermisoDTO> modificarRolPermiso(@PathVariable Integer id, @RequestBody ModificarRolPermisoDTO modificarRolPermisoDTO) {

        Permiso permiso= new Permiso();
        permiso.setId_permiso(modificarRolPermisoDTO.getId_permiso());

        Rol rol= new Rol();
        rol.setId_rol(modificarRolPermisoDTO.getId_rol());
        Rol_Permiso rolPermiso = RolPermisoMapper.actualizarDtoDomain(modificarRolPermisoDTO,permiso,rol);

        Optional<Rol_Permiso> modificarRolPermiso = rolPermisoServicio.modificarObjeto(id,rolPermiso);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarRolPermisoDTO> eliminarRolPermiso(@PathVariable Integer id){

        rolPermisoServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }


    @GetMapping
    public ResponseEntity<List<LeerRolPermisoDTO>> mostrarRolPermiso(){

        List<Rol_Permiso> rolesPermisos= rolPermisoServicio.leerObjetos();

        List<LeerRolPermisoDTO> respuesta=rolesPermisos.stream()
                .map(RolPermisoMapper::leerDTORolPermiso)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }
}