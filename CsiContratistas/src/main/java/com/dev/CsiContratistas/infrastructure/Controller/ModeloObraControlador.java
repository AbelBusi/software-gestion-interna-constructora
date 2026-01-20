package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.ModeloObraServicio;
import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.infrastructure.dto.Material.EliminarMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.Material.LeerMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.Material.ModificarMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.ModeloObra.CrearModeloObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.ModeloObra.EliminarModeloObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.ModeloObra.LeerModeloObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.ModeloObra.ModificarModeloObraDTO;
import com.dev.CsiContratistas.infrastructure.mapper.MaterialMapper;
import com.dev.CsiContratistas.infrastructure.mapper.ModeloObraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/modelos_obras")
@CrossOrigin ("http://localhost:3000/")
public class ModeloObraControlador {

    private final ModeloObraServicio modeloObraServicio;

    @PostMapping
    public ResponseEntity<CrearModeloObraDTO> crearModeloObra(@RequestBody CrearModeloObraDTO crearMaterialDTO) {

        Tipo_obra tipo_obra= new Tipo_obra();

        tipo_obra.setId_tipo_obra(crearMaterialDTO.getId_tipo_obra());

        Modelo_obra materialDomain = ModeloObraMapper.crearDtoDomain(crearMaterialDTO,tipo_obra);

        Modelo_obra crearModeloObra = modeloObraServicio.crearObjeto(materialDomain);

        return new ResponseEntity<>(crearMaterialDTO,HttpStatus.CREATED);

    }


    @GetMapping("/{id}")
    public ResponseEntity<LeerModeloObraDTO> LeerModeloObra(@PathVariable Integer id){

        Optional<Modelo_obra> modeloObra = modeloObraServicio.leerObjeto(id);
        LeerModeloObraDTO leerModeloObraDTO=ModeloObraMapper.leerDTOModeloObra(modeloObra.orElse(null));
        return new ResponseEntity<>(leerModeloObraDTO,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ModificarModeloObraDTO> modificarModeloObra(@PathVariable Integer id, @RequestBody ModificarModeloObraDTO modificarModeloObraDTO) {


        Tipo_obra tipo_obra= new Tipo_obra();

        tipo_obra.setId_tipo_obra(modificarModeloObraDTO.getId_tipo_obra());

        Modelo_obra modeloObra = ModeloObraMapper.actualizarDtoDomain(modificarModeloObraDTO,tipo_obra);

        Optional<Modelo_obra> modificarModeloObra = modeloObraServicio.modificarObjeto(id,modeloObra);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarModeloObraDTO> eliminarModeloObra(@PathVariable Integer id){

        modeloObraServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }


    @GetMapping
    public ResponseEntity<List<LeerModeloObraDTO>> mostrarModeloObra(){

        List<Modelo_obra> modeloObras= modeloObraServicio.leerObjetos();

        List<LeerModeloObraDTO> respuesta=modeloObras.stream()
                .map(ModeloObraMapper::leerDTOModeloObra)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> subirModelo3D(@RequestParam("file") MultipartFile file) throws IOException {

        Path storage = Paths.get("src/main/resources/static/modelos3d");

        // Validar .glb
        if (!file.getOriginalFilename().toLowerCase().endsWith(".glb")) {
            return ResponseEntity.badRequest().body("Formato no soportado");
        }

        // Crear carpeta si no existe
        if (Files.notExists(storage)) Files.createDirectories(storage);

        // Nombre único
        String filename = UUID.randomUUID() + ".glb";
        Path destino = storage.resolve(filename);
        Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        // Devolver URL pública
        String publicUrl = "/modelos3d/" + filename;
        return ResponseEntity.ok(publicUrl);
    }


}