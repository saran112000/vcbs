package com.bookings.vcbs.booking.service;

import java.time.LocalDate;

import java.util.List;


import org.springframework.stereotype.Service;

import com.bookings.vcbs.booking.dto.BookingRequestDTO;
import com.bookings.vcbs.master.dto.EmployeeDTO;



public interface BookingService {

    List saveBooking(BookingRequestDTO dto, Long empId);
    public List<EmployeeDTO> getEmployeeList();
    
}

