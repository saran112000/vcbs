package com.bookings.vcbs.booking.service;

import java.time.LocalDate;

import java.util.List;


import org.springframework.stereotype.Service;


import com.bookings.vcbs.booking.dao.BookingRequestDTO;



public interface BookingService {

    List saveBooking(BookingRequestDTO dto, Long empId);

    
}

