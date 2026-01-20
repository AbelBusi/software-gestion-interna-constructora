package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.ObraServicio;
import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Obra.CrearObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.Obra.LeerObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.Obra.ModificarObraDTO;
import com.dev.CsiContratistas.infrastructure.mapper.ObraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/obras")
@CrossOrigin("http://localhost:3000/")
public class ObraControlador {

    private final ObraServicio obraServicio;

    @PostMapping
    public ResponseEntity<CrearObraDTO> crearObra(@RequestBody CrearObraDTO dto) {

        Empleado responsable = new Empleado();
        responsable.setId_empleado(dto.getId_responsable());

        Modelo_obra modeloObra = new Modelo_obra();
        modeloObra.setId_modelo_obra(dto.getId_modelo_obra());

        Terreno terreno = new Terreno();
        terreno.setId_terreno(dto.getId_terreno());

        Tipo_construccion tipoConstruccion = new Tipo_construccion();
        tipoConstruccion.setId_tipo_construccion(dto.getId_tipo_construccion());

        Obra obra = ObraMapper.crearDtoDomain(dto, responsable, modeloObra, terreno, tipoConstruccion);

        Obra nuevaObra = obraServicio.crearObjeto(obra);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerObraDTO> leerObra(@PathVariable Integer id) {
        Optional<Obra> obra = obraServicio.leerObjeto(id);
        if (obra.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        LeerObraDTO respuesta = ObraMapper.leerDTOObra(obra.get());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modificarObra(@PathVariable Integer id, @RequestBody ModificarObraDTO dto) {

        Empleado responsable = new Empleado();
        responsable.setId_empleado(dto.getId_responsable());

        Modelo_obra modeloObra = new Modelo_obra();
        modeloObra.setId_modelo_obra(dto.getId_modelo_obra());

        Terreno terreno = new Terreno();
        terreno.setId_terreno(dto.getId_terreno());

        Tipo_construccion tipoConstruccion = new Tipo_construccion();
        tipoConstruccion.setId_tipo_construccion(dto.getId_tipo_construccion());

        Obra obraModificada = ObraMapper.actualizarDtoDomain(dto, responsable, modeloObra, terreno, tipoConstruccion);

        obraServicio.modificarObjeto(id, obraModificada);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarObra(@PathVariable Integer id) {
        obraServicio.eliminarObjeto(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<LeerObraDTO>> listarObras() {
        List<Obra> obras = obraServicio.leerObjetos();
        List<LeerObraDTO> respuesta = obras.stream()
                .map(ObraMapper::leerDTOObra)
                .toList();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
