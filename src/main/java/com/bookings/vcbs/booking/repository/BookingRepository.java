
package com.bookings.vcbs.booking.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookings.vcbs.booking.modal.Bookings;


@Repository
public interface BookingRepository extends JpaRepository<Bookings, Long> {

	@Query(value = """
		    SELECT s.slot_time
		    FROM bookings_slot_details s
		    JOIN bookings b ON b.booking_id = s.booking_id
		    WHERE b.room_id = :roomId
		      AND b.booking_date = :bookingDate
		      AND b.status = 'ACTIVE'
		""", nativeQuery = true)
		List<String> findBookedSlots(
		        @Param("roomId") Long roomId,
		        @Param("bookingDate") LocalDateTime bookingDate
		);

}
