package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.EmpleadoServicio;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.infrastructure.Entity.EmpleadoEntidad;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.CrearEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.EliminarEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.LeerEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.ModificarEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.CrearTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.EliminarTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.LeerTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.ModificarTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.mapper.EmpleadoMapper;
import com.dev.CsiContratistas.infrastructure.mapper.TipoMaterialMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin ("http://localhost:3000/")
@RequestMapping("/api/v1/empleados")
public class EmpleadoControlador {

    private final EmpleadoServicio empleadoServicio;

    @PostMapping
    public ResponseEntity<?> crearEmpleado( @Valid @RequestBody CrearEmpleadoDTO crearEmpleadoDTO , BindingResult result) {
        if (result.hasErrors()) {
            String errores = result.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .reduce("", (a, b) -> a + b + "; ");
            return ResponseEntity.badRequest().body(errores);
        }
        if (empleadoServicio.existeDni(crearEmpleadoDTO.getDni())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El DNI ya está registrado");
        }

        Empleado empleadoDomain = EmpleadoMapper.crearDtoDomain(crearEmpleadoDTO);

        Empleado crearEmpleado = empleadoServicio.crearObjeto(empleadoDomain);

        return new ResponseEntity<>(crearEmpleadoDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerEmpleadoDTO> LeerEmpleado(@PathVariable Integer id){

        Optional<Empleado> empleado = empleadoServicio.leerObjeto(id);
        LeerEmpleadoDTO leerEmpleadoDTO=EmpleadoMapper.leerDTOEmpleado(empleado.orElse(null));
        return new ResponseEntity<>(leerEmpleadoDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarEmpleado(@PathVariable Integer id, @Valid @RequestBody ModificarEmpleadoDTO modificarEmpleadoDTO, BindingResult result) {
        if (result.hasErrors()) {
            String errores = result.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .reduce("", (a, b) -> a + b + "; ");
            return ResponseEntity.badRequest().body(errores);
        }

        Optional<Empleado> empleadoExistenteOpt = empleadoServicio.leerObjeto(id);
        if (empleadoExistenteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }

        if (!empleadoExistenteOpt.get().getDni().equals(modificarEmpleadoDTO.getDni())
                && empleadoServicio.existeDni(modificarEmpleadoDTO.getDni())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El DNI ya está registrado por otro empleado");
        }

        Empleado empleado = EmpleadoMapper.actualizarDtoDomain(modificarEmpleadoDTO);
        empleadoServicio.modificarObjeto(id, empleado);

        return ResponseEntity.ok("Empleado modificado correctamente");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Integer id) {
        Optional<Empleado> empleadoOpt = empleadoServicio.leerObjeto(id);
        if (empleadoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"mensaje\": \"Empleado no encontrado\"}");
        }
        empleadoServicio.eliminarObjeto(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("{\"mensaje\": \"Empleado eliminado correctamente\"}");
    }


    @GetMapping
    public ResponseEntity<List<LeerEmpleadoDTO>> mostrarTiposMateriales(){

        List<Empleado> empleados= empleadoServicio.leerObjetos();

        List<LeerEmpleadoDTO> respuesta=empleados.stream()
                .map(EmpleadoMapper::leerDTOEmpleado)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}