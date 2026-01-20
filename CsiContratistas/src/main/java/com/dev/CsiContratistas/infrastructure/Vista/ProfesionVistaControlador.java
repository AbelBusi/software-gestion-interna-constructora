package com.dev.CsiContratistas.infrastructure.Vista;

import com.dev.CsiContratistas.infrastructure.reportes.ProfesionReportePdfService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dev.CsiContratistas.application.Services.ProfesionServicio;
import org.springframework.ui.Model;
import com.dev.CsiContratistas.domain.model.Profesiones;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("administrador/profesiones")

public class ProfesionVistaControlador {
    private final ProfesionServicio profesionServicio;
    private final ProfesionReportePdfService profesionReportePdfService;

    @GetMapping("")
    public String vistaprofesiones(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        Page<Profesiones> profesionesPage = profesionServicio.leerObjetosPaginados(PageRequest.of(page, pageSize));
        model.addAttribute("profesiones", profesionesPage.getContent());
        model.addAttribute("profesionesPage", profesionesPage); // Pasar el objeto Page completo para la paginación
        return "empleados/profesiones";
    }

    @PostMapping("/guardar")
    public String guardarProfesion(@ModelAttribute Profesiones profesion, RedirectAttributes redirectAttributes) {
        try {
            profesionServicio.crearObjeto(profesion);
            redirectAttributes.addFlashAttribute("success", "Profesión guardada exitosamente!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar profesión: " + e.getMessage());
        }
        return "redirect:/administrador/profesiones";
    }

    @PostMapping("/desactivar")
    public String desactivarProfesion(@RequestParam Integer id_profesion, RedirectAttributes redirectAttributes) {
        try {
            profesionServicio.desactivarProfesion(id_profesion);
            redirectAttributes.addFlashAttribute("success", "Profesión desactivada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al desactivar profesión: " + e.getMessage());
        }
        return "redirect:/administrador/profesiones";
    }

    @PostMapping("/activar")
    public String activarProfesion(@RequestParam Integer id_profesion, RedirectAttributes redirectAttributes) {
        try {
            profesionServicio.activarProfesion(id_profesion);
            redirectAttributes.addFlashAttribute("success", "Profesión activada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al activar profesión: " + e.getMessage());
        }
        return "redirect:/administrador/profesiones";
    }

    @PostMapping("/editar")
    public String editarProfesion(@ModelAttribute Profesiones profesion, RedirectAttributes redirectAttributes) {
        try {
            profesionServicio.modificarObjeto(profesion.getId_profesion(), profesion);
            redirectAttributes.addFlashAttribute("success", "Profesión actualizada exitosamente!");
        } catch (RuntimeException e) { // Capturar la excepción del servicio si el nombre ya existe
            redirectAttributes.addFlashAttribute("error", "Error al actualizar profesión: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error inesperado al actualizar profesión: " + e.getMessage());
        }
        return "redirect:/administrador/profesiones";
    }

    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> descargarReportePDF() {
        try {
            List<Profesiones> profesionesParaReporte = profesionServicio.leerObjetos();

            byte[] pdfBytes = profesionReportePdfService.generarReporte(profesionesParaReporte);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=Reporte_profesiones.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
