package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.RolUsuarioServicio;
import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.EliminarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.CrearRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.LeerRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.ModificarRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.CrearRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.EliminarRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.LeerRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.ModificarRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.mapper.RamaMapper;
import com.dev.CsiContratistas.infrastructure.mapper.RolUsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roles_usuarios")
@CrossOrigin ("http://localhost:3000/")
public class RolUsuarioControlador {

    private final RolUsuarioServicio rolUsuarioServicio;

    @PostMapping
    public ResponseEntity<CrearRolUsuarioDTO> crearRolUsuario(@RequestBody CrearRolUsuarioDTO crearRolUsuarioDTO) {

        Usuario usuario= new Usuario();
        usuario.setId_usuario(crearRolUsuarioDTO.getId_usuario());

        Rol rol= new Rol();
        rol.setId_rol(crearRolUsuarioDTO.getId_rol());

        Rol_usuario rolUsuarioDomain = RolUsuarioMapper.crearDtoDomain(crearRolUsuarioDTO,usuario,rol);

        Rol_usuario crearRolUsuario = rolUsuarioServicio.crearObjeto(rolUsuarioDomain);

        return new ResponseEntity<>(crearRolUsuarioDTO,HttpStatus.CREATED);

    }


    @GetMapping("/{id}")
    public ResponseEntity<LeerRolUsuarioDTO> LeerRolUsuario(@PathVariable Integer id){

        Optional<Rol_usuario> rolUsuario = rolUsuarioServicio.leerObjeto(id);
        LeerRolUsuarioDTO leerRolUsuarioDTO=RolUsuarioMapper.leerDTORolUsuario(rolUsuario.orElse(null));
        return new ResponseEntity<>(leerRolUsuarioDTO,HttpStatus.OK);
    }
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<LeerRolUsuarioDTO> obtenerRolPorUsuarioId(@PathVariable Integer idUsuario) {
    Optional<Rol_usuario> rolUsuarioOpt = rolUsuarioServicio.obtenerPorUsuarioId(idUsuario);
    if (rolUsuarioOpt.isPresent()) {
        LeerRolUsuarioDTO dto = RolUsuarioMapper.leerDTORolUsuario(rolUsuarioOpt.get());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ModificarRolUsuarioDTO> modificarRolUsuario(@PathVariable Integer id, @RequestBody ModificarRolUsuarioDTO modificarRolUsuarioDTO) {

        Usuario usuario= new Usuario();
        usuario.setId_usuario(modificarRolUsuarioDTO.getId_usuario());

        Rol rol= new Rol();
        rol.setId_rol(modificarRolUsuarioDTO.getId_rol());
        Rol_usuario rolUsuario = RolUsuarioMapper.actualizarDtoDomain(modificarRolUsuarioDTO,usuario,rol);

        Optional<Rol_usuario> modificarRolUsuario = rolUsuarioServicio.modificarObjeto(id,rolUsuario);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarRolUsuarioDTO> eliminarRolUsuario(@PathVariable Integer id){

        rolUsuarioServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }


    @GetMapping
    public ResponseEntity<List<LeerRolUsuarioDTO>> mostrarRolUsuario(){

        List<Rol_usuario> rolesUsuarios= rolUsuarioServicio.leerObjetos();

        List<LeerRolUsuarioDTO> respuesta=rolesUsuarios.stream()
                .map(RolUsuarioMapper::leerDTORolUsuario)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }
}