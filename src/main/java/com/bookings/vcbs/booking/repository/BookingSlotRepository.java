package com.bookings.vcbs.booking.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookings.vcbs.booking.modal.BookingsSlotDetails;

@Repository
public interface BookingSlotRepository extends JpaRepository<BookingsSlotDetails, Long> {

	List<BookingsSlotDetails> findByBookingId(Long bookingId);
}
