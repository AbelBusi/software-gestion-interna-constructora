package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.FinanciamientoServicio;
import com.dev.CsiContratistas.domain.model.Cliente;
import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.model.Obra;
import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import com.dev.CsiContratistas.infrastructure.Entity.*;
import com.dev.CsiContratistas.infrastructure.Repository.in.*;
import com.dev.CsiContratistas.infrastructure.dto.Financiamiento.*;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.CrearFinanciamientoEmpleadolDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.LeerFinanciamientoEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.CrearFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.LeerFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.mapper.FinanciamientoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/financiamientos")
@CrossOrigin ("http://localhost:3000/")
public class FinanciamientoControlador {

    private final FinanciamientoServicio financiamientoServicio;

    //De forma bruta
    private final IJpaFinanciamientoRepositorio financiamientoRepo;
    private final IJpaFinanciamientoMaterialRepositorio financiamientoMaterialRepo;
    private final IJpaMaterialRepositorio materialRepo;
    private final IJpaClienteRepositorio clienteRepo;
    private final IJpaObraRepositorio obraRepo;
    private final IJpaTipoFinanciamientoRepositorio tipoFinanciamientoRepo;
    private final IJpaEmpleadoRepositorio empleadoRepositorio;
    private final IJpaFinanciamientoEmpleadolRepositorio iJpaFinanciamientoEmpleadolRepositorio;


    @PostMapping
    public ResponseEntity<CrearFinanciamientoDTO> crearFinanciamiento(@RequestBody CrearFinanciamientoDTO crearFinanciamientoDTO) {

        Tipo_financiamiento tipoFinanciamiento = new Tipo_financiamiento();
        tipoFinanciamiento.setId_tipo_financiamiento(crearFinanciamientoDTO.getId_tipo_financiamiento());

        Cliente cliente = new Cliente();
        cliente.setId_cliente(crearFinanciamientoDTO.getId_cliente());

        Obra obra= new Obra();
        obra.setId_obra(crearFinanciamientoDTO.getId_obra());

        Financiamiento financiamientoDomain = FinanciamientoMapper.crearDtoDomain(crearFinanciamientoDTO,tipoFinanciamiento,cliente,obra);

        Financiamiento crearFinanciamiento = financiamientoServicio.crearObjeto(financiamientoDomain);

        return new ResponseEntity<>(crearFinanciamientoDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerFinanciamientoDTO> LeerFinanciamiento(@PathVariable Integer id){

        Optional<Financiamiento> tipoFinanciamiento = financiamientoServicio.leerObjeto(id);
        LeerFinanciamientoDTO leerTipoFinanciamientoDTO=FinanciamientoMapper.leerDTOFinanciamiento(tipoFinanciamiento.orElse(null));
        return new ResponseEntity<>(leerTipoFinanciamientoDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarFinanciamientoDTO> modificarFinanciamiento(@PathVariable Integer id, @RequestBody ModificarFinanciamientoDTO modificarFinanciamientoDTO) {

        Tipo_financiamiento tipoFinanciamiento = new Tipo_financiamiento();
        tipoFinanciamiento.setId_tipo_financiamiento(modificarFinanciamientoDTO.getId_tipo_financiamiento());

        Cliente cliente = new Cliente();
        cliente.setId_cliente(modificarFinanciamientoDTO.getId_cliente());

        Obra obra = new Obra();
        obra.setId_obra(modificarFinanciamientoDTO.getId_obra());

        Financiamiento financiamiento = FinanciamientoMapper.actualizarDtoDomain(modificarFinanciamientoDTO,tipoFinanciamiento,cliente,obra);

        Optional<Financiamiento> modificarFinanciamiento = financiamientoServicio.modificarObjeto(id,financiamiento);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarFinanciamientoDTO> eliminarFinanciamiento(@PathVariable Integer id){

        financiamientoServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerFinanciamientoDTO>> mostrarTiposFinanciamientos(){

        List<Financiamiento> tipoFinanciamientos= financiamientoServicio.leerObjetos();

        List<LeerFinanciamientoDTO> respuesta=tipoFinanciamientos.stream()
                .map(FinanciamientoMapper::leerDTOFinanciamiento)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

    @PostMapping("/completo")
    public ResponseEntity<String> crearFinanciamientoCompleto(@RequestBody CrearFinanciamientoConMaterialesYEmpleadosDTO dto) {

        // 1. Buscar las entidades necesarias
        ClienteEntidad cliente = clienteRepo.findById(dto.getFinanciamiento().getId_cliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        ObraEntidad obra = obraRepo.findById(dto.getFinanciamiento().getId_obra())
                .orElseThrow(() -> new RuntimeException("Obra no encontrada"));
        TipoFinanciamientoEntidad tipo = tipoFinanciamientoRepo.findById(dto.getFinanciamiento().getId_tipo_financiamiento())
                .orElseThrow(() -> new RuntimeException("Tipo de financiamiento no encontrado"));

        // 2. Crear financiamiento
        FinanciamientoEntidad financiamiento = new FinanciamientoEntidad();
        financiamiento.setId_cliente(cliente);
        financiamiento.setId_financiamiento_obra(obra);
        financiamiento.setId_tipo_financiamiento(tipo);
        financiamiento.setCodigo_financiamiento(dto.getFinanciamiento().getCodigo_financiamiento());
        financiamiento.setSub_total(dto.getFinanciamiento().getSub_total());
        financiamiento.setIgv(dto.getFinanciamiento().getIgv());
        financiamiento.setTotal(dto.getFinanciamiento().getTotal());
        financiamiento.setFecha_financiamiento(dto.getFinanciamiento().getFecha_financiamiento());
        financiamiento.setForma_pago(dto.getFinanciamiento().getForma_pago());
        financiamiento.setEstado(dto.getFinanciamiento().getEstado());

        FinanciamientoEntidad guardado = financiamientoRepo.save(financiamiento);

        // 3. Materiales
        for (CrearFinanciamientoMaterialDTO m : dto.getMateriales()) {
            MaterialEntidad material = materialRepo.findById(m.getId_material())
                    .orElseThrow(() -> new RuntimeException("Material no encontrado"));

            FinanciamientoMaterialEntidad relacion = new FinanciamientoMaterialEntidad();
            relacion.setId_financiamiento(guardado);
            relacion.setId_material(material);
            relacion.setCantidad(m.getCantidad());
            relacion.setPrecio_total(m.getPrecio_total());

            financiamientoMaterialRepo.save(relacion);

            material.setStock_actual(material.getStock_actual() - m.getCantidad());
            materialRepo.save(material);
        }

        // 4. Empleados
        for (CrearFinanciamientoEmpleadolDTO e : dto.getEmpleados()) {
            EmpleadoEntidad empleado = empleadoRepositorio.findById(e.getId_empleado())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            FinanciamientoEmpleadoEntidad relacion = new FinanciamientoEmpleadoEntidad();
            relacion.setId_financiamiento(guardado);
            relacion.setId_empleado(empleado);
            relacion.setDias_participacion(e.getDias_participacion());
            relacion.setCosto_total(e.getCosto_total());

            iJpaFinanciamientoEmpleadolRepositorio.save(relacion);
        }

        return new ResponseEntity<>("Financiamiento, materiales y empleados creados correctamente", HttpStatus.CREATED);
    }


    @PutMapping("/completo/{id}")
    public ResponseEntity<String> actualizarFinanciamientoCompleto(
            @PathVariable Integer id,
            @RequestBody CrearFinanciamientoConMaterialesYEmpleadosDTO dto) {

        // 1. Buscar el financiamiento original
        FinanciamientoEntidad financiamiento = financiamientoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Financiamiento no encontrado"));

        // 2. Actualizar entidades relacionadas
        ClienteEntidad cliente = clienteRepo.findById(dto.getFinanciamiento().getId_cliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        ObraEntidad obra = obraRepo.findById(dto.getFinanciamiento().getId_obra())
                .orElseThrow(() -> new RuntimeException("Obra no encontrada"));
        TipoFinanciamientoEntidad tipo = tipoFinanciamientoRepo.findById(dto.getFinanciamiento().getId_tipo_financiamiento())
                .orElseThrow(() -> new RuntimeException("Tipo de financiamiento no encontrado"));

        // 3. Actualizar campos
        financiamiento.setId_cliente(cliente);
        financiamiento.setId_financiamiento_obra(obra);
        financiamiento.setId_tipo_financiamiento(tipo);
        financiamiento.setCodigo_financiamiento(dto.getFinanciamiento().getCodigo_financiamiento());
        financiamiento.setSub_total(dto.getFinanciamiento().getSub_total());
        financiamiento.setIgv(dto.getFinanciamiento().getIgv());
        financiamiento.setTotal(dto.getFinanciamiento().getTotal());
        financiamiento.setFecha_financiamiento(dto.getFinanciamiento().getFecha_financiamiento());
        financiamiento.setForma_pago(dto.getFinanciamiento().getForma_pago());
        financiamiento.setEstado(dto.getFinanciamiento().getEstado());

        financiamientoRepo.save(financiamiento);

        // 4. Eliminar relaciones anteriores
        financiamientoMaterialRepo.eliminarPorFinanciamiento(financiamiento);
        iJpaFinanciamientoEmpleadolRepositorio.eliminarPorFinanciamiento(financiamiento);

        // 5. Insertar nuevos materiales
        for (CrearFinanciamientoMaterialDTO m : dto.getMateriales()) {
            MaterialEntidad material = materialRepo.findById(m.getId_material())
                    .orElseThrow(() -> new RuntimeException("Material no encontrado"));

            FinanciamientoMaterialEntidad relacion = new FinanciamientoMaterialEntidad();
            relacion.setId_financiamiento(financiamiento);
            relacion.setId_material(material);
            relacion.setCantidad(m.getCantidad());
            relacion.setPrecio_total(m.getPrecio_total());

            financiamientoMaterialRepo.save(relacion);
        }

        // 6. Insertar nuevos empleados
        for (CrearFinanciamientoEmpleadolDTO e : dto.getEmpleados()) {
            EmpleadoEntidad empleado = empleadoRepositorio.findById(e.getId_empleado())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            FinanciamientoEmpleadoEntidad relacion = new FinanciamientoEmpleadoEntidad();
            relacion.setId_financiamiento(financiamiento);
            relacion.setId_empleado(empleado);
            relacion.setDias_participacion(e.getDias_participacion());
            relacion.setCosto_total(e.getCosto_total());

            iJpaFinanciamientoEmpleadolRepositorio.save(relacion);
        }

        return ResponseEntity.ok("Financiamiento completo actualizado correctamente");
    }

    @GetMapping("/completo/{id}")
    public ResponseEntity<LeerFinanciamientoCompletoDTO> obtenerFinanciamientoCompleto(@PathVariable Integer id) {

        // Buscar entidad principal
        FinanciamientoEntidad financiamiento = financiamientoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Financiamiento no encontrado"));

        // Convertir a DTO principal
        LeerFinanciamientoDTO financiamientoDTO = new LeerFinanciamientoDTO(
                financiamiento.getId_financiamiento(),
                financiamiento.getId_financiamiento_obra().getId_obra(),
                financiamiento.getId_tipo_financiamiento().getId_tipo_financiamiento(),
                financiamiento.getId_cliente().getId_cliente(),
                financiamiento.getCodigo_financiamiento(),
                financiamiento.getSub_total(),
                financiamiento.getIgv(),
                financiamiento.getTotal(),
                financiamiento.getFecha_financiamiento(),
                financiamiento.getForma_pago(),
                financiamiento.getEstado()
        );

        // Buscar materiales
        List<LeerFinanciamientoMaterialDTO> materiales = financiamientoMaterialRepo
                .buscarPorFinanciamiento(financiamiento)
                .stream()
                .map(material -> new LeerFinanciamientoMaterialDTO(
                        material.getId_financiamiento_material(),
                        material.getId_financiamiento().getId_financiamiento(),
                        material.getId_material().getIdmaterial(),
                        material.getCantidad(),
                        material.getPrecio_total()
                ))

                .toList();

        // Buscar empleados
        List<LeerFinanciamientoEmpleadoDTO> empleados = iJpaFinanciamientoEmpleadolRepositorio
                .buscarPorFinanciamiento(financiamiento)
                .stream()
                .map(empleado -> new LeerFinanciamientoEmpleadoDTO(
                        empleado.getId_financiamiento_empleado(),
                        empleado.getId_financiamiento().getId_financiamiento(),
                        empleado.getId_empleado().getId_empleado(),
                        empleado.getDias_participacion(),
                        empleado.getCosto_total()
                ))
                .collect(Collectors.toList());


        LeerFinanciamientoCompletoDTO completo = new LeerFinanciamientoCompletoDTO(
                financiamientoDTO,
                materiales,
                empleados
        );

        return ResponseEntity.ok(completo);
    }


    @GetMapping("/por-obra/{idObra}")
    public ResponseEntity<LeerFinanciamientoDTO> obtenerFinanciamientoPorObra(@PathVariable Integer idObra) {
        ObraEntidad obra = obraRepo.findById(idObra)
                .orElseThrow(() -> new RuntimeException("Obra no encontrada"));

        FinanciamientoEntidad financiamiento = financiamientoRepo.buscarPorObra(obra)
                .orElseThrow(() -> new RuntimeException("Financiamiento no encontrado"));

        LeerFinanciamientoDTO dto = new LeerFinanciamientoDTO(
                financiamiento.getId_financiamiento(),
                obra.getId_obra(),
                financiamiento.getId_tipo_financiamiento().getId_tipo_financiamiento(),
                financiamiento.getId_cliente().getId_cliente(),
                financiamiento.getCodigo_financiamiento(),
                financiamiento.getSub_total(),
                financiamiento.getIgv(),
                financiamiento.getTotal(),
                financiamiento.getFecha_financiamiento(),
                financiamiento.getForma_pago(),
                financiamiento.getEstado()
        );

        return ResponseEntity.ok(dto);
    }


}