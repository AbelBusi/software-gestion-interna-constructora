package com.dev.CsiContratistas.infrastructure.reportes;

import com.dev.CsiContratistas.domain.model.Material;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MaterialReporteExcelService {

    private static final Color HEADER_COLOR = new Color(70, 130, 180);
    private static final Color ACCENT_COLOR = new Color(100, 149, 237);
    private static final Color LIGHT_GRAY = new Color(240, 240, 240);

    public byte[] generarReporte(List<Material> materiales) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Map<String, CellStyle> styles = createEnhancedStyles(workbook);

            Sheet sheet = workbook.createSheet("Reporte Materiales");
            sheet.setDisplayGridlines(false);

            int rowNum = createEnhancedHeader(workbook, sheet, styles, materiales);
            rowNum = createEnhancedKPIs(sheet, rowNum, materiales, styles);
            createEnhancedMaterialsTable(sheet, rowNum + 2, materiales, styles);

            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            return baos.toByteArray();
        }
    }

    private Map<String, CellStyle> createEnhancedStyles(Workbook workbook) {
        Map<String, CellStyle> styles = new HashMap<>();
        XSSFCellStyle style;
        IndexedColorMap colorMap = new DefaultIndexedColorMap();

        style = (XSSFCellStyle) workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont titleFont = ((XSSFWorkbook) workbook).createFont();
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(titleFont);
        style.setFillForegroundColor(new XSSFColor(HEADER_COLOR, colorMap));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("mainTitle", style);


        style = (XSSFCellStyle) workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont subFont = ((XSSFWorkbook) workbook).createFont();
        subFont.setFontHeightInPoints((short) 10);
        subFont.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFont(subFont);
        styles.put("subtitle", style);


        style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFFont sectionFont = ((XSSFWorkbook) workbook).createFont();
        sectionFont.setFontHeightInPoints((short) 12);
        sectionFont.setBold(true);
        sectionFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(sectionFont);
        style.setFillForegroundColor(new XSSFColor(ACCENT_COLOR, colorMap));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        styles.put("sectionHeader", style);


        style = (XSSFCellStyle) workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(new XSSFColor(LIGHT_GRAY, colorMap));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        XSSFFont kpiFont = ((XSSFWorkbook) workbook).createFont();
        kpiFont.setFontHeightInPoints((short) 10);
        kpiFont.setBold(true);
        style.setFont(kpiFont);
        styles.put("kpi", style);

        style = (XSSFCellStyle) workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(new XSSFColor(HEADER_COLOR, colorMap));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont tableHeaderFont = ((XSSFWorkbook) workbook).createFont();
        tableHeaderFont.setFontHeightInPoints((short) 10);
        tableHeaderFont.setBold(true);
        tableHeaderFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(tableHeaderFont);
        styles.put("tableHeader", style);


        style = (XSSFCellStyle) workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styles.put("tableData", style);


        style = (XSSFCellStyle) workbook.createCellStyle();
        style.cloneStyleFrom(styles.get("tableData"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setDataFormat(workbook.createDataFormat().getFormat("0"));
        styles.put("number", style);


        style = (XSSFCellStyle) workbook.createCellStyle();
        style.cloneStyleFrom(styles.get("tableData"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setDataFormat(workbook.createDataFormat().getFormat("\"S/\"#,##0.00"));
        styles.put("currency", style);

        return styles;
    }

    private int createEnhancedHeader(Workbook workbook, Sheet sheet, Map<String, CellStyle> styles, List<Material> materiales) {
        Row logoRow = sheet.createRow(0);
        logoRow.setHeightInPoints(30);
        Cell logoCell = logoRow.createCell(0);
        logoCell.setCellValue("CSI CONTRATISTAS");
        logoCell.setCellStyle(styles.get("mainTitle"));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        Row titleRow = sheet.createRow(1);
        titleRow.setHeightInPoints(24);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("REPORTE DE MATERIALES");
        titleCell.setCellStyle(styles.get("mainTitle"));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

        Row dateRow = sheet.createRow(2);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue("Generado el: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                " | Total de materiales: " + materiales.size());
        dateCell.setCellStyle(styles.get("subtitle"));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));

        return 4;
    }

    private int createEnhancedKPIs(Sheet sheet, int rowNum, List<Material> materiales, Map<String, CellStyle> styles) {
        Row sectionRow = sheet.createRow(rowNum++);
        sectionRow.setHeightInPoints(20);
        Cell sectionCell = sectionRow.createCell(0);
        sectionCell.setCellValue("INDICADORES CLAVE");
        sectionCell.setCellStyle(styles.get("sectionHeader"));
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 5));

        int stockTotal = materiales.stream().mapToInt(m -> m.getStock_actual() != null ? m.getStock_actual() : 0).sum();
        BigDecimal valorTotal = materiales.stream()
                .map(m -> m.getPrecio_unitario() != null && m.getStock_actual() != null
                        ? m.getPrecio_unitario().multiply(BigDecimal.valueOf(m.getStock_actual()))
                        : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Material mayorStock = materiales.stream()
                .max(Comparator.comparingInt(m -> m.getStock_actual() != null ? m.getStock_actual() : 0))
                .orElse(null);

        Row kpiRow = sheet.createRow(rowNum++);
        kpiRow.setHeightInPoints(50);

        Cell kpi1Cell = kpiRow.createCell(0);
        kpi1Cell.setCellValue("STOCK TOTAL\n" + stockTotal);
        kpi1Cell.setCellStyle(styles.get("kpi"));

        Cell kpi2Cell = kpiRow.createCell(1);
        kpi2Cell.setCellValue("VALOR TOTAL\nS/ " + String.format("%,.2f", valorTotal));
        kpi2Cell.setCellStyle(styles.get("kpi"));

        Cell kpi3Cell = kpiRow.createCell(2);
        String mayorStockInfo = mayorStock != null ?
                mayorStock.getNombre() + "\n(Stock: " + mayorStock.getStock_actual() + ")" : "N/A";
        kpi3Cell.setCellValue("MAYOR STOCK\n" + mayorStockInfo);
        kpi3Cell.setCellStyle(styles.get("kpi"));

        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 4, 5));

        return rowNum + 1;
    }

    private void createEnhancedMaterialsTable(Sheet sheet, int rowNum, List<Material> materiales, Map<String, CellStyle> styles) {
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.setHeightInPoints(20);

        String[] headers = {"ID", "MATERIAL", "TIPO", "STOCK", "PRECIO UNIT.", "VALOR TOTAL"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styles.get("tableHeader"));
        }

        for (Material m : materiales) {
            Row dataRow = sheet.createRow(rowNum++);

            Cell idCell = dataRow.createCell(0);
            idCell.setCellValue(m.getId_material());
            idCell.setCellStyle(styles.get("number"));

            Cell nameCell = dataRow.createCell(1);
            nameCell.setCellValue(m.getNombre());
            nameCell.setCellStyle(styles.get("tableData"));

            Cell typeCell = dataRow.createCell(2);
            typeCell.setCellValue(m.getTipo_material() != null ? m.getTipo_material().getNombre() : "-");
            typeCell.setCellStyle(styles.get("tableData"));

            Cell stockCell = dataRow.createCell(3);
            stockCell.setCellValue(m.getStock_actual() != null ? m.getStock_actual() : 0);
            stockCell.setCellStyle(styles.get("number"));

            Cell priceCell = dataRow.createCell(4);
            priceCell.setCellValue(m.getPrecio_unitario() != null ? m.getPrecio_unitario().doubleValue() : 0);
            priceCell.setCellStyle(styles.get("currency"));

            Cell totalCell = dataRow.createCell(5);
            BigDecimal total = m.getPrecio_unitario() != null && m.getStock_actual() != null ?
                    m.getPrecio_unitario().multiply(BigDecimal.valueOf(m.getStock_actual())) :
                    BigDecimal.ZERO;
            totalCell.setCellValue(total.doubleValue());
            totalCell.setCellStyle(styles.get("currency"));
        }

        Row lastRow = sheet.createRow(rowNum);
        for (int i = 0; i < 6; i++) {
            Cell cell = lastRow.createCell(i);
            cell.setCellStyle(styles.get("tableHeader"));
        }
    }
}
