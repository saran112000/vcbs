package com.bookings.vcbs.report.service;

import java.time.LocalDate;
import java.util.List;

import com.bookings.vcbs.report.projection.BookingDetailProjection;


public interface ReportService {

	List<BookingDetailProjection> getRoomBookedList(String status, LocalDate fromDate, LocalDate toDate);

	byte[] generatePdf(String string, List<BookingDetailProjection> bookingReport, String fromDate, String toDate) throws Exception;

	byte[] generateExcel(List<BookingDetailProjection> bookingReport) throws Exception;

}
