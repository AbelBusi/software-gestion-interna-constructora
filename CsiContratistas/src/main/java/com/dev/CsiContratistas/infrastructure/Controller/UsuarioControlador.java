package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.UsuarioServicio;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.EliminarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.CrearRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.LeerRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.ModificarRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.CrearUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.EliminarUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.LeerUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.ModificarUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.mapper.RamaMapper;
import com.dev.CsiContratistas.infrastructure.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin ("http://localhost:3000/")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;


    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid  @RequestBody CrearUsuarioDTO crearUsuarioDTO , BindingResult result) {

        if (result.hasErrors()) {
            String errores = result.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .reduce("", (a, b) -> a + b + "; ");
            return ResponseEntity.badRequest().body(errores);
        }
        if (usuarioServicio.existByEmpleado(crearUsuarioDTO.getId_empleado())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un  usuario asignado.");
        }
        if (usuarioServicio.existCorreo(crearUsuarioDTO.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya está registrado en otro usuario.");
        }

        Empleado empleado = new Empleado();
        empleado.setId_empleado(crearUsuarioDTO.getId_empleado());

        Usuario usuario = UsuarioMapper.crearDtoDomain(crearUsuarioDTO, empleado);
        if(usuario.getFecha_asignacion()==null){
            usuario.setFecha_asignacion(LocalDateTime.now());
        }
        usuarioServicio.crearObjeto(usuario);
        LeerUsuarioDTO leerUsuarioDTO = UsuarioMapper.leerDTOUsuario(usuario);

        return new ResponseEntity<>(leerUsuarioDTO, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> LeerUsuario(@PathVariable Integer id){

        Optional<Usuario> usuario = usuarioServicio.leerObjeto(id);
        if(usuario.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Usuarion no encontrado con id "+id);

        }
        LeerUsuarioDTO leerUsuarioDTO=UsuarioMapper.leerDTOUsuario(usuario.get());
        return new ResponseEntity<>(leerUsuarioDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarUsuario(@PathVariable Integer id, @RequestBody ModificarUsuarioDTO modificarUsuarioDTO) {
        try {
            Optional<Usuario> usuarioExiste = usuarioServicio.leerObjeto(id);
            if(usuarioExiste.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("El usuario con id " + id + " no se encuentra");
            }

            Usuario usuarioActual = usuarioExiste.get();


            if (!modificarUsuarioDTO.getId_empleado().equals(usuarioActual.getId_empleado().getId_empleado())) {
                if (usuarioServicio.existByEmpleadoExcludingUsuario(modificarUsuarioDTO.getId_empleado(), id)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("El id de empleado ya está registrado en otra cuenta de usuario.");
                }
            }


            if (!modificarUsuarioDTO.getCorreo().equals(usuarioActual.getCorreo())) {
                if (usuarioServicio.existCorreo(modificarUsuarioDTO.getCorreo())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("El correo ya está registrado en otro usuario.");
                }
            }

            Empleado empleado = new Empleado();
            empleado.setId_empleado(modificarUsuarioDTO.getId_empleado());

            Usuario usuario = UsuarioMapper.actualizarDtoDomain(modificarUsuarioDTO, empleado);
            usuario.setId_usuario(id);

            if(usuario.getFecha_asignacion() == null){
                usuario.setFecha_asignacion(LocalDateTime.now());
            }

            Optional<Usuario> usuarioModificado = usuarioServicio.modificarObjeto(id, usuario);
            if (usuarioModificado.isPresent()) {
                return new ResponseEntity<>(UsuarioMapper.leerDTOUsuario(usuarioModificado.get()), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al actualizar el usuario");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor al procesar la solicitud");
        }




    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id){
        try {
            Optional<Usuario> usuarioExiste = usuarioServicio.leerObjeto(id);
            if(usuarioExiste.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado con id " + id);
            }

            boolean eliminado = usuarioServicio.eliminarObjeto(id) > 0;

            if(eliminado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("No se pudo eliminar el usuario");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar usuario: " + e.getMessage());
        }


    }


    @GetMapping
    public ResponseEntity<List<LeerUsuarioDTO>> mostrarRamas(){

        List<Usuario> usuarios= usuarioServicio.leerObjetos();

        List<LeerUsuarioDTO> respuesta=usuarios.stream()
                .map(UsuarioMapper::leerDTOUsuario)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }
}