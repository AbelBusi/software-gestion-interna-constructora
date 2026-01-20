package com.dev.CsiContratistas.infrastructure.reportes;

import com.dev.CsiContratistas.infrastructure.dto.Usuario.UsuarioVistaDTO;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.knowm.xchart.*;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioReportePdfService {

    private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
    private static final Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.GRAY);
    private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
    private static final Font KPI_TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.DARK_GRAY);
    private static final Font KPI_VALUE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLUE);
    private static final Color HEADER_BG_COLOR = new Color(51, 102, 153);
    private static final Color KPI_BORDER_COLOR = new Color(220, 220, 220);

    public byte[] generarReporte(List<UsuarioVistaDTO> usuarios) throws DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate());

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            agregarEncabezado(document);
            agregarKPIs(document, usuarios);
            agregarTabla(document, usuarios);
            agregarGraficos(document, usuarios);
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new DocumentException("Error al generar el PDF de usuarios: " + e.getMessage());
        } finally {
            if (document.isOpen()) document.close();
        }
    }

    private void agregarEncabezado(Document document) throws DocumentException {
        try {
            Image logo = Image.getInstance(getClass().getResource("/static/img/logo-report.png"));
            logo.scaleToFit(80, 80);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception ignored) {}

        document.add(Chunk.NEWLINE);

        Paragraph empresa = new Paragraph("CSI CONTRATISTAS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE));
        empresa.setAlignment(Element.ALIGN_CENTER);
        document.add(empresa);

        Paragraph titulo = new Paragraph("Reporte General de Usuarios", TITLE_FONT);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        Paragraph fecha = new Paragraph(
                "Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                SUBTITLE_FONT);
        fecha.setAlignment(Element.ALIGN_CENTER);
        document.add(fecha);

        document.add(Chunk.NEWLINE);
    }

    private void agregarKPIs(Document document, List<UsuarioVistaDTO> usuarios) throws DocumentException {
        int totalUsuarios = usuarios.size();
        long activos = usuarios.stream().filter(u -> Boolean.TRUE.equals(u.getEstado())).count();
        long inactivos = usuarios.stream().filter(u -> Boolean.FALSE.equals(u.getEstado())).count();
        String rolMasFrecuente = usuarios.stream()
                .collect(Collectors.groupingBy(UsuarioVistaDTO::getRol, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("N/A");
        String ultimoCreado = usuarios.stream()
                .sorted((a, b) -> b.getIdUsuario().compareTo(a.getIdUsuario()))
                .map(UsuarioVistaDTO::getCorreo)
                .findFirst().orElse("N/A");

        PdfPTable kpiTable = new PdfPTable(5);
        kpiTable.setWidthPercentage(100);
        kpiTable.setSpacingBefore(10f);
        kpiTable.setSpacingAfter(10f);

        kpiTable.addCell(crearCeldaKPI("Total de Usuarios", String.valueOf(totalUsuarios)));
        kpiTable.addCell(crearCeldaKPI("Activos", String.valueOf(activos)));
        kpiTable.addCell(crearCeldaKPI("Inactivos", String.valueOf(inactivos)));
        kpiTable.addCell(crearCeldaKPI("Rol Más Frecuente", rolMasFrecuente));
        kpiTable.addCell(crearCeldaKPI("Último Usuario Creado", ultimoCreado));

        document.add(kpiTable);
        document.add(Chunk.NEWLINE);
    }

    private PdfPCell crearCeldaKPI(String titulo, String valor) {
        Paragraph p = new Paragraph();
        p.add(new Chunk(titulo + "\n", KPI_TITLE_FONT));
        p.add(new Chunk(valor, KPI_VALUE_FONT));

        PdfPCell cell = new PdfPCell(p);
        cell.setPadding(10);
        cell.setBorderColor(KPI_BORDER_COLOR);
        cell.setBackgroundColor(Color.WHITE);
        cell.setBorderWidth(1.5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private void agregarTabla(Document document, List<UsuarioVistaDTO> usuarios) throws DocumentException {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[]{1, 3, 3, 2, 2, 3, 2, 2});

        String[] headers = {"ID", "Nombre", "Apellidos", "DNI", "Teléfono", "Correo", "Rol", "Estado"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
            cell.setBackgroundColor(HEADER_BG_COLOR);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        }

        for (UsuarioVistaDTO u : usuarios) {
            table.addCell(crearCelda(u.getIdUsuario()));
            table.addCell(crearCelda(u.getEmpleadoNombre()));
            table.addCell(crearCelda(u.getEmpleadoApellidos()));
            table.addCell(crearCelda(u.getDni()));
            table.addCell(crearCelda(u.getTelefono()));
            table.addCell(crearCelda(u.getCorreo()));
            table.addCell(crearCelda(u.getRol()));
            table.addCell(crearCelda(u.getEstado() ? "Activo" : "Inactivo"));
        }

        document.add(table);
    }

    private PdfPCell crearCelda(Object contenido) {
        PdfPCell cell = new PdfPCell(new Phrase(contenido != null ? contenido.toString() : ""));
        cell.setPadding(5);
        cell.setBorderColor(KPI_BORDER_COLOR);
        return cell;
    }

    private void agregarGraficos(Document document, List<UsuarioVistaDTO> usuarios) throws Exception {
        document.newPage();
        document.add(new Paragraph("Gráficos", TITLE_FONT));
        document.add(Chunk.NEWLINE);

        Image graficoPieRol = generarGraficoPie("Usuarios por Rol",
                usuarios.stream().map(UsuarioVistaDTO::getRol).collect(Collectors.toList()));
        graficoPieRol.setAlignment(Image.ALIGN_CENTER);
        document.add(graficoPieRol);

        Image graficoPieEstado = generarGraficoPie("Usuarios Activos vs Inactivos",
                usuarios.stream().map(u -> u.getEstado() ? "Activo" : "Inactivo").collect(Collectors.toList()));
        graficoPieEstado.setAlignment(Image.ALIGN_CENTER);
        document.add(graficoPieEstado);
    }

    private Image generarGraficoPie(String titulo, List<String> categorias) throws Exception {
        Map<String, Long> data = categorias.stream()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        PieChart chart = new PieChartBuilder().width(600).height(400).title(titulo).build();
        PieStyler styler = chart.getStyler();
        styler.setLegendVisible(true);
        styler.setPlotContentSize(0.7);
        styler.setChartTitleVisible(true);
        styler.setChartTitleFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 14));
        styler.setLegendFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 10));
        styler.setDecimalPattern("#");

        data.forEach(chart::addSeries);

        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        return Image.getInstance(img, null);
    }
}
