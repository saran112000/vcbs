package com.bookings.vcbs.booking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookings.vcbs.booking.dto.BookingRequestDTO;
import com.bookings.vcbs.booking.dto.CancelBookingDTO;
import com.bookings.vcbs.booking.modal.Bookings;
import com.bookings.vcbs.booking.modal.BookingsSlotDetails;
import com.bookings.vcbs.booking.repository.BookingRepository;
import com.bookings.vcbs.booking.repository.BookingSlotRepository;
import com.bookings.vcbs.master.dto.EmployeeDTO;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingSlotRepository bookingSlotRepository;
    
    @Transactional

    @Override
    public long saveBooking(BookingRequestDTO dto, Long empId) {

        // 1. Save the main Booking Header
        Bookings booking = new Bookings();
        booking.setRoomId(dto.getRoomId());
        booking.setBookingDate(dto.getBookingDate());
        booking.setGuestName(dto.getGuest());
        booking.setSubject(dto.getSubject());
        booking.setBookingBy(empId);
        booking.setCreatedBy(empId);
        booking.setCreatedDate(LocalDateTime.now());
        booking.setStatus("ACTIVE");

     
        booking = bookingRepository.save(booking);

        
        if (dto.getSlot() != null && !dto.getSlot().isEmpty()) {
            for (String slotTimeRange : dto.getSlot()) {
                BookingsSlotDetails slotDetail = new BookingsSlotDetails();
                slotDetail.setBookingId(booking.getBookingId());
                
             
                slotDetail.setSlotTime(slotTimeRange); 
                slotDetail.setIsActive(1);
                
                bookingSlotRepository.save(slotDetail);
            }
        }

        return booking.getBookingId(); 
    }
    
    
    @Override
    public List getFilteredEmployees(String role, Long divisionId) {
      
        if ("ROLE_ADMIN".equals(role)) {
            return bookingRepository.findAllActiveEmployees(); 
        } else {
            return bookingRepository.findEmployeesByDivision(divisionId);
        }
    }

    @Override
    public void cancelBooking(CancelBookingDTO cancelDTO, Long empId) {
        
        Bookings booking = bookingRepository.findById(cancelDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

     
        booking.setStatus("CANCELLED");
        booking.setModified_by(cancelDTO.getUserName());
        booking.setModifieDate(LocalDateTime.now());
        bookingRepository.save(booking);
        

       
        List<BookingsSlotDetails> slots = bookingSlotRepository.findByBookingId(cancelDTO.getBookingId());
        
        if (slots != null) {
            for (BookingsSlotDetails slot : slots) {
                slot.setIsActive(0); 
                slot.setCancelleBy(empId);
                slot.setCancelledDate(LocalDateTime.now());
                slot.setRemarks(cancelDTO.getReason());
                slot.setModifieDate(LocalDateTime.now());
                slot.setModifieBy(cancelDTO.getUserName());
                
                bookingSlotRepository.save(slot);
            }
        }
    }

}

