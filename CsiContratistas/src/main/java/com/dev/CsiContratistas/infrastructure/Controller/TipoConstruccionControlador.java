package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.TipoConstruccionServicio;
import com.dev.CsiContratistas.domain.model.Tipo_construccion;
import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.CrearTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.EliminarTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.LeerTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.ModificarTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.CrearTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.EliminarTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.LeerTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.ModificarTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.mapper.TipoConstruccionMapper;
import com.dev.CsiContratistas.infrastructure.mapper.TipoSueloMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tipos_construcciones")
@CrossOrigin ("http://localhost:3000/")
public class TipoConstruccionControlador {

    private final TipoConstruccionServicio tipoConstruccionServicio;

    @PostMapping
    public ResponseEntity<CrearTipoConstruccionDTO> crearTipoConstruccion(@RequestBody CrearTipoConstruccionDTO crearTipoConstruccionDTO) {

        Tipo_construccion tipoConstruccionDomain = TipoConstruccionMapper.crearDtoDomain(crearTipoConstruccionDTO);

        Tipo_construccion crearTipoCopnstruccion = tipoConstruccionServicio.crearObjeto(tipoConstruccionDomain);

        return new ResponseEntity<>(crearTipoConstruccionDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerTipoConstruccionDTO> LeerTipoConstruccion(@PathVariable Integer id){

        Optional<Tipo_construccion> tipoConstruccion = tipoConstruccionServicio.leerObjeto(id);
        LeerTipoConstruccionDTO leerTipoConstruccionDTO=TipoConstruccionMapper.leerDTOTipoConstruccion(tipoConstruccion.orElse(null));
        return new ResponseEntity<>(leerTipoConstruccionDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarTipoConstruccionDTO> modificarTipoConstruccion(@PathVariable Integer id, @RequestBody ModificarTipoConstruccionDTO modificarTipoConstruccionDTO) {

        Tipo_construccion tipoConstruccion = TipoConstruccionMapper.actualizarDtoDomain(modificarTipoConstruccionDTO);

        Optional<Tipo_construccion> modificarTipoConstruccion = tipoConstruccionServicio.modificarObjeto(id,tipoConstruccion);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarTipoConstruccionDTO> eliminarTipoConstruccion(@PathVariable Integer id){

        tipoConstruccionServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerTipoConstruccionDTO>> mostrarTiposConstruccion(){

        List<Tipo_construccion> tipoConstruccions= tipoConstruccionServicio.leerObjetos();

        List<LeerTipoConstruccionDTO> respuesta=tipoConstruccions.stream()
                .map(TipoConstruccionMapper::leerDTOTipoConstruccion)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}