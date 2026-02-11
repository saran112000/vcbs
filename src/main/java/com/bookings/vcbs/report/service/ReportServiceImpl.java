package com.bookings.vcbs.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import com.bookings.vcbs.report.projection.BookingDetailProjection;
import com.bookings.vcbs.report.repository.BookingReportRepository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	private BookingReportRepository bookingReportRepository;
	
	@Autowired
    private TemplateEngine templateEngine;
	
	@Override
	public List<BookingDetailProjection> getRoomBookedList(String status,int isActive, LocalDate fromDate, LocalDate todate) {
		System.out.println("status****"+status);
		System.out.println("fromDate****"+fromDate);
		System.out.println("todate****"+todate);
		System.out.println("todate****"+isActive);
		
		return bookingReportRepository.getRoomBookedList(status, isActive, fromDate, todate);
	}
	
    
    public byte[] generatePdf(String templateName, List<BookingDetailProjection> list, String from, String to , String filePrefix ,String nameby) throws Exception {
        Context context = new Context();
        context.setVariable("bookingReport", list);
        context.setVariable("fromDate", from);
        context.setVariable("toDate", to);
        context.setVariable("filePrefix", filePrefix);
        context.setVariable("nameby", nameby);
        
        
        
        String htmlContent = templateEngine.process(templateName, context);
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        }
    }

    public byte[] generateExcel(List<BookingDetailProjection> list) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Determine if this is a cancelled report based on the first item's status (or list properties)
            boolean isCancelled = !list.isEmpty() && "CANCELLED".equalsIgnoreCase(list.get(0).getStatus());
            String sheetName = isCancelled ? "Cancelled Booking Report" : "Active Booking Report";
            Sheet sheet = workbook.createSheet(sheetName);

            // 1. STYLES
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle wrapStyle = workbook.createCellStyle();
            wrapStyle.setWrapText(true);
            wrapStyle.setVerticalAlignment(VerticalAlignment.TOP);

            // 2. HEADERS
            // Dynamic headers based on report type
            String[] headers;
            if (isCancelled) {
                headers = new String[]{"Sl No", "Date", "Room Info", "Guest & Subject", "Cancelled By", "Remarks", "Time Slots"};
            } else {
                headers = new String[]{"Sl No", "Date", "Room Info", "Guest & Subject", "Booked By", "Time Slots"};
            }

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // 3. FILL DATA
            int rowIdx = 1;
            for (BookingDetailProjection b : list) {
                Row row = sheet.createRow(rowIdx++);
                
                row.createCell(0).setCellValue(rowIdx - 1 + ".");
                row.createCell(1).setCellValue(b.getBookingDate() != null ? b.getBookingDate().toString() : "-");

                // Room Info
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(b.getRoomNo() + "\n(" + (b.getRoomType() != null ? b.getRoomType() : "-") + ")");
                cell2.setCellStyle(wrapStyle);

                // Guest & Subject
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(b.getGuestName() + "\nSub: " + (b.getSubject() != null ? b.getSubject() : "-"));
                cell3.setCellStyle(wrapStyle);

                if (isCancelled) {
                    // Cancelled Report Columns
                    row.createCell(4).setCellValue(b.getCancelledBy() != null ? b.getCancelledBy() : "N/A");
                    Cell cell5 = row.createCell(5);
                    cell5.setCellValue(b.getRemarks() != null ? b.getRemarks() : "-");
                    cell5.setCellStyle(wrapStyle);
                    row.createCell(6).setCellValue(b.getBookedSlots());
                } else {
                    // Active Report Columns
                    String bookedBy = (b.getSlotBookedBy() != null ? b.getSlotBookedBy() : "-") + 
                                      (b.getSlotBookedByDesignation() != null ? " [" + b.getSlotBookedByDesignation() + "]" : "");
                    row.createCell(4).setCellValue(bookedBy);
                    row.createCell(5).setCellValue(b.getBookedSlots());
                }
            }

            // 4. Formatting
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                if (i >= 2 && i <= 5) sheet.setColumnWidth(i, 8000); // Give width to text areas
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        style.setFont(font);
        return style;
    }

}
