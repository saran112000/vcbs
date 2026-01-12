package com.bookings.vcbs.report.controller;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context; // Use Thymeleaf Context
import org.xhtmlrenderer.pdf.ITextRenderer; // This uses your Flying Saucer dependency

@Controller
public class ReportController {

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf() throws Exception {
        
        Context context = new Context();
        context.setVariables(Map.of(
            "title", "Invoiced Report",
            "customerName", "John Doe",
            "amount", "$1,250.00"
        ));

        String htmlContent = templateEngine.process("reports/pdf-template", context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            byte[] pdfBytes = outputStream.toByteArray();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        }
    }
}