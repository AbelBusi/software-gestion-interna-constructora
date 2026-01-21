package com.dev.CsiContratistas.infrastructure.Vista;

import com.dev.CsiContratistas.application.Services.EmpleadoServicio;
import com.dev.CsiContratistas.application.Services.ProfesionServicio;
import com.dev.CsiContratistas.application.Services.CargoServicio;
import com.dev.CsiContratistas.application.Services.RamaServicio;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.infrastructure.reportes.EmpleadoReportePdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("administrador/empleados")
@RequiredArgsConstructor

public class EmpleadoVistaControlador {

        private final EmpleadoServicio empleadoServicio;
        private final ProfesionServicio profesionServicio;
        private final CargoServicio cargoServicio;
        private final RamaServicio ramaServicio;
        private final EmpleadoReportePdfService empleadoReportePdfService;

        @GetMapping("")
        public String vistaEmpleados(Model model, @RequestParam(defaultValue = "0") int page) {
                int pageSize = 10;
                Page<Empleado> empleadosPage = empleadoServicio.leerObjetosPaginados(PageRequest.of(page, pageSize));

                List<Cargo> cargos = cargoServicio.leerObjetos();
                List<Profesiones> profesiones = profesionServicio.leerObjetos();
                List<Rama> ramas = ramaServicio.leerObjetos();

                List<Empleado.EmpleadoVista> empleadosVista = empleadosPage.getContent().stream().map(e -> {
                        Optional<Cargo> cargoOpt = cargos.stream()
                                        .filter(c -> c.getId_empleado() != null && c.getId_empleado().getId_empleado()
                                                        .equals(e.getId_empleado()))
                                        .findFirst();

                        String profesionNombre = "";
                        Integer profesionId = null;
                        String ramaNombre = "";
                        Integer ramaId = null;
                        Integer cargoId = null;

                        if (cargoOpt.isPresent()) {
                                Cargo cargo = cargoOpt.get();
                                cargoId = cargo.getId_cargo();
                                if (cargo.getId_profesion() != null) {
                                        profesionNombre = cargo.getId_profesion().getNombre();
                                        profesionId = cargo.getId_profesion().getId_profesion();

                                }

                                if (cargo.getId_rama() != null) {
                                        ramaNombre = cargo.getId_rama().getNombre();
                                        ramaId = cargo.getId_rama().getId_rama();

                                }

                        }

                        return new Empleado.EmpleadoVista(

                                        e.getId_empleado(),
                                        e.getNombre(),
                                        e.getApellidos(),
                                        e.getDni(),
                                        e.getFecha_nacimiento(),
                                        e.getGenero(),
                                        e.getTelefono(),
                                        e.getEstado_civil(),
                                        e.getDireccion(),
                                        e.getEstado(),
                                        profesionNombre,
                                        ramaNombre,
                                        profesionId,
                                        ramaId,
                                        cargoId

                        );

                }).toList();

                model.addAttribute("empleados", empleadosVista);
                model.addAttribute("empleadosPage", empleadosPage);
                model.addAttribute("profesiones", profesiones);
                model.addAttribute("cargos", cargos);
                model.addAttribute("ramas", ramas);

                return "empleados/empleados";

        }

        @PostMapping("/guardar")
        
        public String guardarEmpleado(

                        @RequestParam("dni") String dni,
                        @RequestParam("nombre") String nombre,
                        @RequestParam("apellidos") String apellidos,
                        @RequestParam("fecha_nacimiento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
                        @RequestParam("genero") String genero,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("estado_civil") String estadoCivil,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("id_profesion") Integer idProfesion,
                        @RequestParam("id_rama") Integer idRama,
                        RedirectAttributes redirectAttributes

        ) {
                if (empleadoServicio.existeDni(dni)) {
                        redirectAttributes.addFlashAttribute("error", "El DNI '" + dni + "' ya está registrado.");
                        return "redirect:/administrador/empleados";

                }

                Profesiones profesion = profesionServicio.leerObjeto(idProfesion)

                                .orElseThrow(() -> new RuntimeException("Profesión no encontrada"));

                Rama rama = ramaServicio.leerObjeto(idRama)

                                .orElseThrow(() -> new RuntimeException("Rama no encontrada"));

                Empleado empleado = new Empleado();
                empleado.setDni(dni);
                empleado.setNombre(nombre);
                empleado.setApellidos(apellidos);
                empleado.setFecha_nacimiento(fechaNacimiento);
                empleado.setGenero(genero);
                empleado.setTelefono(telefono);
                empleado.setEstado_civil(estadoCivil);
                empleado.setDireccion(direccion);
                empleado.setEstado("Activo");
                Empleado empleadoGuardado = empleadoServicio.crearObjeto(empleado);
                Cargo cargo = new Cargo();
                cargo.setId_empleado(empleadoGuardado);
                cargo.setId_rama(rama);
                cargo.setId_profesion(profesion);
                cargo.setFecha_asignacion(LocalDateTime.now());
                cargo.setEstado(true); 

                cargoServicio.crearObjeto(cargo);

                redirectAttributes.addFlashAttribute("success", "Empleado registrado correctamente.");

                return "redirect:/administrador/empleados";

        }

        @PostMapping("/editar")

        public String editarEmpleado(

                        @RequestParam("id_empleado") Integer idEmpleado,
                        @RequestParam("dni") String dni,
                        @RequestParam("nombre") String nombre,
                        @RequestParam("apellidos") String apellidos,
                        @RequestParam("fecha_nacimiento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
                        @RequestParam("genero") String genero,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("estado_civil") String estadoCivil,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("estado") String estado,
                        @RequestParam("id_profesion") Integer idProfesion,
                        @RequestParam("id_rama") Integer idRama,
                        @RequestParam(value = "id_cargo", required = false) Integer idCargo,

                        RedirectAttributes redirectAttributes

        ) {

                Profesiones profesion = profesionServicio.leerObjeto(idProfesion)

                                .orElseThrow(() -> new RuntimeException("Profesión no encontrada"));

                Rama rama = ramaServicio.leerObjeto(idRama)

                                .orElseThrow(() -> new RuntimeException("Rama no encontrada"));

                Empleado empleado = empleadoServicio.leerObjeto(idEmpleado).orElse(null);

                if (empleado == null) {

                        redirectAttributes.addFlashAttribute("error", "Empleado no encontrado.");

                        return "redirect:/administrador/empleados";

                }

                if (!empleado.getDni().equals(dni) && empleadoServicio.existeDni(dni)) {

                        redirectAttributes.addFlashAttribute("error",
                                        "El DNI '" + dni + "' ya está registrado por otro empleado.");

                        return "redirect:/administrador/empleados";

                }

                empleado.setDni(dni);
                empleado.setNombre(nombre);
                empleado.setApellidos(apellidos);
                empleado.setFecha_nacimiento(fechaNacimiento);
                empleado.setGenero(genero);
                empleado.setTelefono(telefono);
                empleado.setEstado_civil(estadoCivil);
                empleado.setDireccion(direccion);
                empleado.setEstado(estado);
                empleadoServicio.modificarObjeto(idEmpleado, empleado);


                Cargo cargoActual;
                if (idCargo != null) {
                        cargoActual = cargoServicio.leerObjeto(idCargo).orElse(null);
                } else {
                        cargoActual = cargoServicio.leerObjetos().stream()

                                        .filter(c -> c.getId_empleado() != null
                                                        && c.getId_empleado().getId_empleado().equals(idEmpleado))

                                        .findFirst()
                                        .orElse(null);

                }

                if (cargoActual != null) {

                        cargoActual.setId_rama(rama);
                        cargoActual.setId_profesion(profesion);
                        cargoActual.setFecha_asignacion(LocalDateTime.now()); 
                        cargoServicio.modificarObjeto(cargoActual.getId_cargo(), cargoActual);
                } else {

                        Cargo nuevoCargo = new Cargo();
                        nuevoCargo.setId_empleado(empleado);
                        nuevoCargo.setId_rama(rama);
                        nuevoCargo.setId_profesion(profesion);
                        nuevoCargo.setFecha_asignacion(LocalDateTime.now());
                        nuevoCargo.setEstado(true); 
                        cargoServicio.crearObjeto(nuevoCargo);

                }

                redirectAttributes.addFlashAttribute("success", "Empleado actualizado correctamente.");

                return "redirect:/administrador/empleados";

        }

        @PostMapping("/eliminar/{id}")

        public String eliminarEmpleado(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
                cargoServicio.leerObjetos().stream()
                                .filter(c -> c.getId_empleado() != null
                                                && c.getId_empleado().getId_empleado().equals(id))
                                .forEach(c -> cargoServicio.eliminarObjeto(c.getId_cargo()));
                Integer resultado = empleadoServicio.eliminarObjeto(id); 

                if (resultado != null && resultado == 1) {
                        redirectAttributes.addFlashAttribute("success", "Empleado eliminado correctamente.");
                } else {
                        redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el empleado o no existe.");
                }
                return "redirect:/administrador/empleados";
        }

        @GetMapping("/reporte/pdf")
        public ResponseEntity<byte[]> descargarReportePDF() {
                try {
                        List<Empleado> empleados = empleadoServicio.leerObjetos();
                        List<Cargo> cargos = cargoServicio.leerObjetos();
                        List<Profesiones> profesiones = profesionServicio.leerObjetos();
                        List<Rama> ramas = ramaServicio.leerObjetos();
                        List<Empleado.EmpleadoVista> empleadosVista = empleados.stream().map(e -> {
                                Optional<Cargo> cargoOpt = cargos.stream()
                                                .filter(c -> c.getId_empleado() != null && c.getId_empleado()
                                                                .getId_empleado().equals(e.getId_empleado()))
                                                .findFirst();

                                String profesionNombre = cargoOpt.flatMap(c -> Optional.ofNullable(c.getId_profesion())
                                                .map(Profesiones::getNombre)).orElse("-");
                                String ramaNombre = cargoOpt
                                                .flatMap(c -> Optional.ofNullable(c.getId_rama()).map(Rama::getNombre))
                                                .orElse("-");

                                return new Empleado.EmpleadoVista(
                                                e.getId_empleado(),
                                                e.getNombre(),
                                                e.getApellidos(),
                                                e.getDni(),
                                                e.getFecha_nacimiento(),
                                                e.getGenero(),
                                                e.getTelefono(),
                                                e.getEstado_civil(),
                                                e.getDireccion(),
                                                e.getEstado(),
                                                profesionNombre,
                                                ramaNombre,
                                                null, null, null);
                        }).toList();

                        byte[] pdfBytes = empleadoReportePdfService.generarReporte(empleadosVista);

                        return ResponseEntity.ok()
                                        .header("Content-Disposition", "attachment; filename=Reporte_empleados.pdf")
                                        .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                                        .body(pdfBytes);

                } catch (Exception e) {
                        return ResponseEntity.internalServerError().build();
                }
        }

}