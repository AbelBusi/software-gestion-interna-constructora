package com.dev.CsiContratistas.infrastructure.Vista;

import com.itextpdf.text.pdf.draw.LineSeparator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@RequestMapping("/administrador/gestion-bd")
@RequiredArgsConstructor
public class BDGestorVistaController {

    private final JdbcTemplate jdbc;

    @Value("${spring.datasource.username}")
    private String usuario;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    private String obtenerHost() {
        try {
            URI uri = new URI(url.replace("jdbc:", ""));
            return uri.getHost();
        } catch (URISyntaxException e) {
            throw new RuntimeException("No se pudo extraer el host de la URL de conexión", e);
        }
    }

    private String obtenerPuerto() {
        try {
            URI uri = new URI(url.replace("jdbc:", ""));
            return uri.getPort() != -1 ? String.valueOf(uri.getPort()) : "5432";
        } catch (URISyntaxException e) {
            throw new RuntimeException("No se pudo extraer el puerto de la URL de conexión", e);
        }
    }

    private String obtenerNombreBD() {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    @GetMapping
    public String mostrarPanelBD(Model model) {
        String version = jdbc.queryForObject("SELECT version()", String.class);
        String size = jdbc.queryForObject("SELECT pg_size_pretty(pg_database_size(current_database()))", String.class);

        model.addAttribute("versionBD", version);
        model.addAttribute("tamanoBD", size);
        return "dashboard/gestionBD";
    }

    @GetMapping("/exportar")
    public void exportarBD(@RequestParam(required = false) String fecha, HttpServletResponse response) throws IOException {
        String nombreArchivo = "backup_" + (fecha != null ? fecha : "completo") + ".sql";
        String baseDatos = obtenerNombreBD();
        String host = obtenerHost();
        String puerto = obtenerPuerto();

        ProcessBuilder pb = new ProcessBuilder(
                "pg_dump", "-h", host, "-p", puerto, "-U", usuario, "-F", "p", baseDatos
        );

        pb.environment().put("PGPASSWORD", password);
        pb.redirectErrorStream(true);

        Process process = pb.start();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

        try (InputStream in = process.getInputStream(); OutputStream out = response.getOutputStream()) {
            StreamUtils.copy(in, out);
        }
    }

    @PostMapping("/restaurar")
    public String restaurarBD(@RequestParam("archivoBackup") MultipartFile archivo, Model model) throws IOException {
        if (archivo.isEmpty()) {
            model.addAttribute("error", "Archivo vacío");
            return "redirect:/administrador/gestion-bd";
        }

        String baseDatos = obtenerNombreBD();
        String host = obtenerHost();
        String puerto = obtenerPuerto();

        File tempFile = File.createTempFile("restore-", ".sql");
        archivo.transferTo(tempFile);

        ProcessBuilder pb = new ProcessBuilder(
                "psql", "-h", host, "-p", puerto, "-U", usuario, "-d", baseDatos, "-f", tempFile.getAbsolutePath()
        );

        pb.environment().put("PGPASSWORD", password);
        pb.redirectErrorStream(true);

        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            reader.lines().forEach(System.out::println);
        }

        return "redirect:/administrador/gestion-bd";
    }

    @GetMapping("/reporte-pdf")
    public void generarPDF(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_bd_csi.pdf");

        Document document = new Document(PageSize.A4, 50, 50, 60, 50);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();



        // Colores y fuentes personalizados
        BaseColor azulCSI = new BaseColor(0, 76, 153); // Azul institucional CSI
        Font tituloPrincipal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, azulCSI);
        Font subtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, azulCSI);
        Font textoNormal = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Font textoGris = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.DARK_GRAY);
        Font textoNegritaAzul = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, azulCSI);

        // Encabezado principal
        Paragraph titulo = new Paragraph("📄 Manual Técnico - Detalle de la Base de Datos", tituloPrincipal);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(new Paragraph("\n")); // espacio

        // Información general del sistema
        document.add(new Paragraph("📘 Información General:", subtitulo));
        document.add(new Paragraph("Nombre del Sistema: CSI Contratistas", textoNormal));
        document.add(new Paragraph("Nombre de la Base de Datos: " + obtenerNombreBD(), textoNormal));
        document.add(new Paragraph("Host: " + obtenerHost(), textoNormal));
        document.add(new Paragraph("Puerto: " + obtenerPuerto(), textoNormal));
        document.add(new Paragraph("Usuario de Conexión: " + usuario, textoNormal));
        document.add(new Paragraph("Versión PostgreSQL: " + jdbc.queryForObject("SELECT version()", String.class), textoNormal));
        document.add(new Paragraph("Tamaño Total: " + jdbc.queryForObject("SELECT pg_size_pretty(pg_database_size(current_database()))", String.class), textoNormal));

        document.add(new Paragraph("\n✏️ Observaciones del Administrador:", textoGris));
        document.add(new Paragraph(
                "El presente reporte ha sido generado automáticamente por el sistema de gestión de CSI Contratistas con el fin de mantener una documentación clara, ordenada y verificable "
                        + "sobre la estructura actual de la base de datos. Se recomienda a los usuarios responsables del área de tecnología revisar periódicamente los registros y estructuras para "
                        + "garantizar la integridad, consistencia y seguridad de la información almacenada.\n\n"
                        + "Asimismo, se hace énfasis en que toda modificación o mantenimiento estructural debe ser documentado y comunicado a los jefes de área y al equipo de desarrollo. Este procedimiento "
                        + "busca prevenir errores humanos y asegurar que cualquier cambio sea trazable dentro del marco de buenas prácticas del ciclo de vida del software. \n\n"
                        + "Finalmente, este reporte forma parte del conjunto de entregables exigidos en las auditorías internas y externas de calidad que CSI Contratistas realiza periódicamente para cumplir con "
                        + "las normas técnicas establecidas y seguir garantizando un servicio confiable y transparente a nuestros clientes e inversionistas.", textoGris));

        document.add(new Paragraph("\n"));

// Tabla de registros por entidad
        document.add(new Paragraph("📦 Información de Tablas y Registros", subtitulo));

        List<String> tablas = jdbc.queryForList("SELECT tablename FROM pg_tables WHERE schemaname='public'", String.class);
        for (String tabla : tablas) {
            Integer cantidad = jdbc.queryForObject("SELECT COUNT(*) FROM " + tabla, Integer.class);
            document.add(new Paragraph("📁 Tabla: " + tabla.toUpperCase(), textoNegritaAzul));
            document.add(new Paragraph("   🔢 Registros: " + cantidad, textoNormal));
            document.add(new Paragraph("   ───────────────────────────────────────", textoGris));
        }


// Relaciones entre tablas
        document.add(new Paragraph("\n🔗 Relaciones entre Entidades (Llaves Foráneas)", subtitulo));

        List<Map<String, Object>> relaciones = jdbc.queryForList(
                "SELECT con.conname AS constraint_name, " +
                        "       cl.relname AS tabla_origen, " +
                        "       att.attname AS campo_origen, " +
                        "       cl2.relname AS tabla_referencia, " +
                        "       att2.attname AS campo_referencia " +
                        "FROM pg_constraint con " +
                        "JOIN pg_class cl ON con.conrelid = cl.oid " +
                        "JOIN pg_attribute att ON att.attrelid = cl.oid AND att.attnum = ANY(con.conkey) " +
                        "JOIN pg_class cl2 ON con.confrelid = cl2.oid " +
                        "JOIN pg_attribute att2 ON att2.attrelid = cl2.oid AND att2.attnum = ANY(con.confkey) " +
                        "WHERE con.contype = 'f';"
        );

        for (Map<String, Object> rel : relaciones) {
            String nombre = (String) rel.get("constraint_name");
            String tablaOrigen = (String) rel.get("tabla_origen");
            String campoOrigen = (String) rel.get("campo_origen");
            String tablaRef = (String) rel.get("tabla_referencia");
            String campoRef = (String) rel.get("campo_referencia");

            document.add(new Paragraph("🔸 Restricción: " + nombre, textoNegritaAzul));
            document.add(new Paragraph("   🔄 " + tablaOrigen + "." + campoOrigen + " ➝ " + tablaRef + "." + campoRef, textoNormal));
            document.add(new Paragraph("   ───────────────────────────────────────", textoGris));
        }


        document.add(new Paragraph("\nComentario Tecnico: ", textoGris));
        document.add(new Paragraph(
                "En esta sección se registran los comentarios técnicos relevantes relacionados con la estructura, mantenimiento y desempeño de la base de datos en cuestión. Es importante que "
                        + "los desarrolladores, administradores de base de datos y personal técnico anoten cualquier hallazgo significativo, comportamiento inesperado, o posibles oportunidades de mejora "
                        + "detectadas durante la inspección o monitoreo del sistema.\n\n"
                        + "También se recomienda dejar constancia de cambios recientes en la arquitectura, ajustes en el modelo entidad-relación, optimización de queries o configuraciones especiales que "
                        + "hayan sido implementadas para mejorar el rendimiento o la seguridad. Toda esta información es crucial para garantizar la trazabilidad de las decisiones técnicas y para facilitar "
                        + "el trabajo colaborativo entre los distintos equipos involucrados en el proyecto.", textoGris));

        // Footer
        document.add(new Paragraph("\n"));
        LineSeparator ls = new LineSeparator();
        ls.setLineColor(azulCSI);
        document.add(ls);
        Paragraph footer = new Paragraph("Fin del reporte - CSI Contratistas ©", textoGris);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
    }

    @GetMapping("/documentacion-api")
    public String mostrarDocumentacion() {
        return "dashboard/gestion-api"; // Vista thymeleaf con botones a Swagger
    }

}

