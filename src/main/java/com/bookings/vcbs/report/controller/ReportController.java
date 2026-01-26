package com.bookings.vcbs.report.controller;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context; // Use Thymeleaf Context
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.bookings.vcbs.config.WebMvcConfigruation;
import com.bookings.vcbs.master.dto.MainModuleDTO;
import com.bookings.vcbs.master.dto.SubModuleDTO;
import com.bookings.vcbs.master.service.MasterService;
import com.bookings.vcbs.report.projection.BookingDetailProjection;
import com.bookings.vcbs.report.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReportController {

    private final WebMvcConfigruation webMvcConfigruation;

    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private MasterService masterService;

    ReportController(WebMvcConfigruation webMvcConfigruation) {
        this.webMvcConfigruation = webMvcConfigruation;
    }
    
    @GetMapping("/reports/bookings")
    public String bookingReportList(
            @RequestParam(value = "fromDate", required = false) String fromDate, 
            @RequestParam(value = "toDate", required = false) String toDate, 
            Model model) {
        
    	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (fromDate == null || toDate == null) {
            LocalDate now = LocalDate.now();
            fromDate = now.withDayOfMonth(1).format(inputFormatter);
            toDate = now.format(inputFormatter);
        }
        LocalDate startDate = LocalDate.parse(fromDate, inputFormatter);
        LocalDate endDate = LocalDate.parse(toDate, inputFormatter);
        
        List<BookingDetailProjection> bookingReport = reportService.getRoomBookedList("ACTIVE", startDate, endDate);
        bookingReport.forEach(row->System.out.println("-----------"+row));
        model.addAttribute("bookingReport", bookingReport != null ? bookingReport : new ArrayList<>());
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        
        model.addAttribute("mainModuleList", masterService.getMainModuleList());
        model.addAttribute("subModuleList", masterService.getSubModuleList());
        
        return "reports/bookingReportList";
    }

    @PostMapping("/booking-report")
    public void exportReport(@RequestParam("fromDate") String fromDate, 
                             @RequestParam("toDate") String toDate, 
                             @RequestParam("actionType") String actionType, 
                             HttpServletResponse response) throws Exception {
        
    	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	LocalDate startDate = LocalDate.parse(fromDate, inputFormatter);
        LocalDate endDate = LocalDate.parse(toDate, inputFormatter);
        
        List<BookingDetailProjection> bookingReport = reportService.getRoomBookedList("ACTIVE", startDate, endDate);
        
        // Optional: Filter the list by fromDate/toDate here if your service doesn't do it via SQL
        
        if ("P".equals(actionType)) {
            byte[] pdfBytes = reportService.generatePdf("reports/booking_report_pdf", bookingReport, fromDate, toDate);
            response.setContentType("application/pdf");
            // Change 'attachment' to 'inline'
            response.setHeader("Content-Disposition", "inline; filename=BookingReport.pdf");
            response.getOutputStream().write(pdfBytes);
        } else if ("E".equals(actionType)) {
            byte[] excelBytes = reportService.generateExcel(bookingReport);
            // Note: Most browsers cannot 'preview' Excel inline and will still download it.
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "inline; filename=BookingReport.xlsx");
            response.getOutputStream().write(excelBytes);
        }
        response.getOutputStream().flush();
    }
}