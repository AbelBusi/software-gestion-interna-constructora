package com.dev.CsiContratistas.infrastructure.reportes;

import org.springframework.stereotype.Service;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.CategoryStyler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmpleadoReportePdfService {

    private static final com.lowagie.text.Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
    private static final com.lowagie.text.Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.GRAY);
    private static final com.lowagie.text.Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
    private static final com.lowagie.text.Font KPI_TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.DARK_GRAY);
    private static final com.lowagie.text.Font KPI_VALUE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLUE);
    private static final Color HEADER_BG_COLOR = new Color(51, 102, 153);
    private static final Color KPI_BORDER_COLOR = new Color(220, 220, 220);

    public byte[] generarReporte(List<Empleado.EmpleadoVista> empleados) throws DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate());
        
        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            agregarEncabezado(document);
            agregarKPIs(document, empleados);
            agregarTablaEmpleados(document, empleados);
            agregarGraficos(document, empleados);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new DocumentException("Error al generar el reporte PDF: " + e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                baos.close();
            } catch (Exception ex) {
            }
        }
    }

    private void agregarEncabezado(Document document) throws DocumentException {
        try {
            
            com.lowagie.text.Image logo = com.lowagie.text.Image.getInstance(getClass().getResource("/static/img/logo-report.png"));
            logo.scaleToFit(80, 80);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
        }

        
        document.add(Chunk.NEWLINE);
        Paragraph empresa = new Paragraph("CSI CONTRATISTAS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE));
        empresa.setAlignment(Element.ALIGN_CENTER);
        document.add(empresa);

        Paragraph titulo = new Paragraph("Reporte General de Empleados", TITLE_FONT);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        Paragraph fecha = new Paragraph(
                "Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                SUBTITLE_FONT);
        fecha.setAlignment(Element.ALIGN_CENTER);
        document.add(fecha);
        document.add(Chunk.NEWLINE);
    }

    private void agregarKPIs(Document document, List<Empleado.EmpleadoVista> empleados) throws DocumentException {
        int total = empleados.size();
        long activos = empleados.stream().filter(e -> "Activo".equalsIgnoreCase(e.getEstado())).count();
        long inactivos = total - activos;
        
        String profesionPrincipal = empleados.stream()
                .filter(e -> e.getProfesionNombre() != null)
                .collect(Collectors.groupingBy(Empleado.EmpleadoVista::getProfesionNombre, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("N/A");

        PdfPTable kpiTable = new PdfPTable(4);
        kpiTable.setWidthPercentage(100);
        kpiTable.setSpacingBefore(10f);
        kpiTable.setSpacingAfter(10f);
        
        kpiTable.addCell(crearCeldaKPI("Total Empleados", String.valueOf(total)));
        kpiTable.addCell(crearCeldaKPI("Activos", String.valueOf(activos)));
        kpiTable.addCell(crearCeldaKPI("Inactivos", String.valueOf(inactivos)));
        kpiTable.addCell(crearCeldaKPI("Profesión Principal", profesionPrincipal));

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

    private void agregarTablaEmpleados(Document document, List<Empleado.EmpleadoVista> empleados) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[]{1, 3, 2, 3, 2, 2});

        String[] headers = {"ID", "Nombre Completo", "DNI", "Profesion", "Rama", "Estado"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
            cell.setBackgroundColor(HEADER_BG_COLOR);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        }

        for (Empleado.EmpleadoVista e : empleados) {
            table.addCell(crearCeldaDatos(String.valueOf(e.getId_empleado())));
            table.addCell(crearCeldaDatos(e.getNombre() + " " + e.getApellidos()));
            table.addCell(crearCeldaDatos(e.getDni()));
            table.addCell(crearCeldaDatos(Optional.ofNullable(e.getProfesionNombre()).orElse("-")));
            table.addCell(crearCeldaDatos(Optional.ofNullable(e.getRamaNombre()).orElse("-")));
            
            PdfPCell estadoCell = crearCeldaDatos(e.getEstado());
            estadoCell.setBackgroundColor("Activo".equalsIgnoreCase(e.getEstado()) 
                ? new Color(220, 255, 220) 
                : new Color(255, 220, 220));
            table.addCell(estadoCell);
        }

        document.add(table);
    }

    private PdfPCell crearCeldaDatos(String contenido) {
        PdfPCell cell = new PdfPCell(new Phrase(contenido));
        cell.setPadding(5);
        cell.setBorderColor(KPI_BORDER_COLOR);
        return cell;
    }

    private void agregarGraficos(Document document, List<Empleado.EmpleadoVista> empleados) throws DocumentException {
        document.newPage();
        document.add(new Paragraph("Gráficos", TITLE_FONT));
        document.add(Chunk.NEWLINE);

        try {
            com.lowagie.text.Image chartRama = generarGraficoPie("Distribución por Área", 
                    empleados.stream().map(Empleado.EmpleadoVista::getRamaNombre).toList());
            chartRama.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
            document.add(chartRama);

            com.lowagie.text.Image chartProf = generarGraficoBarras("Distribución por Profesión", 
                    empleados.stream().map(Empleado.EmpleadoVista::getProfesionNombre).toList());
            chartProf.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
            document.add(chartProf);
        } catch (Exception e) {
            throw new DocumentException("Error al generar gráficos: " + e.getMessage());
        }
    }

    private com.lowagie.text.Image generarGraficoPie(String titulo, List<String> categorias) throws Exception {
        Map<String, Long> conteo = categorias.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        PieChart chart = new PieChartBuilder()
                .width(600)
                .height(400)
                .title(titulo)
                .theme(Styler.ChartTheme.XChart)
                .build();

        chart.getStyler()
                .setLegendVisible(true)
                .setPlotContentSize(0.7)
                .setChartTitleVisible(true)
                .setChartTitleFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 14))
                .setLegendFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 10));

        conteo.forEach((key, value) -> chart.addSeries(key, value));

        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        return com.lowagie.text.Image.getInstance(img, null);
    }

    private com.lowagie.text.Image generarGraficoBarras(String titulo, List<String> categorias) throws Exception {
        Map<String, Long> conteo = categorias.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(conteo.entrySet());
        sortedEntries.sort(Map.Entry.<String, Long>comparingByValue().reversed());

        List<String> keys = sortedEntries.stream().map(entry -> entry.getKey()).collect(Collectors.toList());
        List<Long> values = sortedEntries.stream().map(entry -> entry.getValue()).collect(Collectors.toList());

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(500)
                .title(titulo)
                .xAxisTitle("Profesión")
                .yAxisTitle("Cantidad de Empleados")
                .theme(Styler.ChartTheme.XChart)
                .build();

        CategoryStyler styler = chart.getStyler();
        styler.setLegendVisible(false);
        styler.setHasAnnotations(true);
        styler.setPlotGridVerticalLinesVisible(false);
        styler.setPlotGridHorizontalLinesVisible(true);
        styler.setAxisTitlesVisible(true);
        styler.setChartTitleFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 14));
        styler.setAxisTitleFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 12));
        styler.setAxisTickLabelsFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 10));

        chart.addSeries("Profesiones", keys, values);

        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        return com.lowagie.text.Image.getInstance(img, null);
    }
}