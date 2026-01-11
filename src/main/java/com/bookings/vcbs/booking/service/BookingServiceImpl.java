package com.bookings.vcbs.booking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookings.vcbs.booking.dao.BookingRequestDTO;
import com.bookings.vcbs.booking.modal.Bookings;
import com.bookings.vcbs.booking.modal.BookingsSlotDetails;
import com.bookings.vcbs.booking.repository.BookingRepository;
import com.bookings.vcbs.booking.repository.BookingSlotRepository;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingSlotRepository bookingSlotRepository;

    @Override
    public List saveBooking(BookingRequestDTO dto, Long empId) {

        Bookings booking = new Bookings();
        booking.setRoomId(dto.getRoomId());
        booking.setBookingDate(dto.getBookingDate());
        booking.setGuestName(dto.getGuest());
        booking.setSubject(dto.getSubject());
        booking.setBookingBy(empId);
        booking.setCreatedBy(empId);
        booking.setCreatedDate(LocalDateTime.now());
        booking.setStatus("ACTIVE");

        bookingRepository.save(booking);

        BookingsSlotDetails slot = new BookingsSlotDetails();
        slot.setBookingId(booking.getBookingId());
        slot.setSlotTime(dto.getSlotTime());

        bookingSlotRepository.save(slot);
		return null;
		
    }


}

