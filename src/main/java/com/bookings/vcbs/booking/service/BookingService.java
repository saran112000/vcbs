package com.bookings.vcbs.booking.service;

import java.time.LocalDate;

import java.util.List;


import org.springframework.stereotype.Service;

import com.bookings.vcbs.booking.dto.BookingRequestDTO;
import com.bookings.vcbs.booking.dto.CancelBookingDTO;
import com.bookings.vcbs.booking.projection.EmployeeProjection;
import com.bookings.vcbs.master.dto.EmployeeDTO;



public interface BookingService {

    long saveBooking(BookingRequestDTO dto, Long empId);
   
        List<EmployeeProjection> getFilteredEmployees(String role, Long divisionId);
       
   
        void cancelBooking(CancelBookingDTO cancelDTO, Long empId);

		
    
}

