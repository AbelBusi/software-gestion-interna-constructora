package com.dev.CsiContratistas.infrastructure.Vista;

import com.dev.CsiContratistas.application.Services.ProfesionServicio;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.infrastructure.reportes.RamaReportePdfService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.dev.CsiContratistas.application.Services.RamaServicio;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("administrador/ramas")

public class RamaVistaControlador {
    private final RamaServicio ramaServicio;
    private final RamaReportePdfService ramaReportePdfService;
    private final ProfesionServicio profesionServicio;

    @GetMapping("")
    public String mostrarRamas(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id_rama") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String estadoFilter,
            @RequestParam(required = false) Integer profesionFilter) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);

        Boolean estado = null;
        if (estadoFilter != null && !estadoFilter.isEmpty()) {
            estado = Boolean.parseBoolean(estadoFilter);
        }

        Page<Rama> ramaPage = ramaServicio.leerRamasPaginadas(search, estado, profesionFilter, pageable);

        model.addAttribute("ramas", ramaPage.getContent());
        model.addAttribute("ramaPage", ramaPage);
        model.addAttribute("profesiones", profesionServicio.leerObjetos());

        model.addAttribute("search", search);
        model.addAttribute("estadoFilter", estadoFilter);
        model.addAttribute("profesionFilter", profesionFilter);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);

        return "empleados/ramas";
    }

    @PostMapping("/guardar")
    public String guardarRama(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam(name = "id_profesion") Integer idProfesion,
            RedirectAttributes redirectAttributes) {
        try {
            Rama nuevaRama = new Rama();
            nuevaRama.setNombre(nombre);
            nuevaRama.setDescripcion(descripcion);

            Optional<Profesiones> profesionOpt = profesionServicio.leerObjeto(idProfesion);
            if (profesionOpt.isPresent()) {
                nuevaRama.setId_profesion(profesionOpt.get());
            } else {
                throw new RuntimeException("Profesión con ID " + idProfesion + " no encontrada.");
            }

            nuevaRama.setFecha_asignacion(LocalDateTime.now());
            nuevaRama.setEstado(true);

            ramaServicio.crearObjeto(nuevaRama);
            redirectAttributes.addFlashAttribute("success", "Rama guardada exitosamente!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar rama: " + e.getMessage());
        }
        return "redirect:/administrador/ramas";
    }

    @PostMapping("/editar")
    public String editarRama(
            @RequestParam Integer id_rama,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam(name = "id_profesion") Integer idProfesion,
            @RequestParam boolean estado,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Rama> ramaExistenteOpt = ramaServicio.leerObjeto(id_rama);
            if (!ramaExistenteOpt.isPresent()) {
                throw new RuntimeException("Rama con ID " + id_rama + " no encontrada para editar.");
            }
            Rama ramaAEditar = ramaExistenteOpt.get();

            ramaAEditar.setNombre(nombre);
            ramaAEditar.setDescripcion(descripcion);

            Optional<Profesiones> profesionOpt = profesionServicio.leerObjeto(idProfesion);
            if (profesionOpt.isPresent()) {
                ramaAEditar.setId_profesion(profesionOpt.get());
            } else {
                throw new RuntimeException("Profesión con ID " + idProfesion + " no encontrada.");
            }
            ramaAEditar.setEstado(estado);

            ramaServicio.modificarObjeto(ramaAEditar.getId_rama(), ramaAEditar);
            redirectAttributes.addFlashAttribute("success", "Rama actualizada exitosamente!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar rama: " + e.getMessage());
        }
        return "redirect:/administrador/ramas";
    }

    @PostMapping("/desactivar")
    public String desactivarRama(@RequestParam Integer idRama, RedirectAttributes redirectAttributes) {
        try {
            ramaServicio.desactivarRama(idRama);
            redirectAttributes.addFlashAttribute("success", "Rama desactivada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al desactivar rama: " + e.getMessage());
        }
        return "redirect:/administrador/ramas";
    }

    @PostMapping("/activar")
    public String activarRama(@RequestParam Integer idRama, RedirectAttributes redirectAttributes) {
        try {
            ramaServicio.activarRama(idRama);
            redirectAttributes.addFlashAttribute("success", "Rama activada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al activar rama: " + e.getMessage());
        }
        return "redirect:/administrador/ramas";
    }

    @PostMapping("/eliminar")
    public String eliminarRama(@RequestParam Integer id_rama, RedirectAttributes redirectAttributes) {
        try {
            ramaServicio.eliminarObjeto(id_rama);
            redirectAttributes.addFlashAttribute("success", "Rama eliminada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar rama: " + e.getMessage());
        }
        return "redirect:/administrador/ramas";
    }

    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> descargarReportePDF() {
        try {
            List<Rama> ramasParaReporte = ramaServicio.leerObjetos();
            byte[] pdfBytes = ramaReportePdfService.generarReporte(ramasParaReporte);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=Reporte_ramas.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
