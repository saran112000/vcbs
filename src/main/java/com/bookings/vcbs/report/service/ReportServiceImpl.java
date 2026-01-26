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
	public List<BookingDetailProjection> getRoomBookedList(String status, LocalDate fromDate, LocalDate todate) {
		System.out.println("status****"+status);
		System.out.println("fromDate****"+fromDate);
		System.out.println("todate****"+todate);
		return bookingReportRepository.getRoomBookedList(status, fromDate, todate);
	}
	
    
    public byte[] generatePdf(String templateName, List<BookingDetailProjection> list, String from, String to) throws Exception {
        Context context = new Context();
        context.setVariable("bookingReport", list);
        context.setVariable("fromDate", from);
        context.setVariable("toDate", to);
        
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
            Sheet sheet = workbook.createSheet("Booking Report");

            // 1. Create Cell Styles
            // Header Style
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Muted/Small Text Style (for Room Type and Subject)
            CellStyle mutedStyle = workbook.createCellStyle();
            Font mutedFont = workbook.createFont();
            mutedFont.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
            mutedFont.setFontHeightInPoints((short) 9);
            mutedStyle.setFont(mutedFont);
            mutedStyle.setWrapText(true);

            // Badge-like Style (for Slots)
            CellStyle badgeStyle = workbook.createCellStyle();
            badgeStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
            badgeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            badgeStyle.setAlignment(HorizontalAlignment.CENTER);
            badgeStyle.setBorderBottom(BorderStyle.THIN);
            badgeStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

            // 2. Define Headers (Matching your HTML <th>)
            String[] headers = {"Sl No", "Booked Date", "Room Info", "Guest & Subject", "Booked By", "Time Slots"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // 3. Fill Data
            int rowIdx = 1;
            for (BookingDetailProjection b : list) {
                Row row = sheet.createRow(rowIdx++);

                // Sl No
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(rowIdx - 1 + ".");
                
                // Booked Date
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(b.getBookingDate() != null ? b.getBookingDate().toString() : "-");

                // Room Info (Room No + Room Type)
                Cell cell2 = row.createCell(2);
                String roomInfo = b.getRoomNo() + "\n(" + (b.getRoomType() != null ? b.getRoomType() : "-") + ")";
                cell2.setCellValue(roomInfo);
                cell2.setCellStyle(mutedStyle); // Uses wrap text to show type under room no

                // Guest & Subject
                Cell cell3 = row.createCell(3);
                String guestSub = b.getGuestName() + "\nSub: " + (b.getSubject() != null ? b.getSubject() : "-");
                cell3.setCellValue(guestSub);
                cell3.setCellStyle(mutedStyle);

                // Booked By (Name + Designation)
                Cell cell4 = row.createCell(4);
                String bookedBy = (b.getSlotBookedBy() != null ? b.getSlotBookedBy() : "-") + 
                                  (b.getSlotBookedByDesignation() != null ? " [" + b.getSlotBookedByDesignation() + "]" : "");
                cell4.setCellValue(bookedBy);

                // Time Slots (The "Badge")
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(b.getBookedSlots() != null ? b.getBookedSlots() : "-");
                cell5.setCellStyle(badgeStyle);
            }

            // 4. Auto-size and formatting adjustments
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                // Give extra width to combined columns
                if(i == 2 || i == 3 || i == 4) sheet.setColumnWidth(i, 8000); 
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

}
