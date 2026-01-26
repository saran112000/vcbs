package com.bookings.vcbs.booking.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.bookings.vcbs.booking.projection.BookingRoomTypeProjection;
import com.bookings.vcbs.master.modal.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
	
	@Query(value ="SELECT room_type_id as roomTypeId , room_type  as roomType, is_active as isActive FROM room_type WHERE is_active = 1", nativeQuery = true)
	List<BookingRoomTypeProjection> findActiveRoomsType();

	
	@Query("SELECT s.slotTime FROM BookingsSlotDetails s " +
		       "JOIN Bookings b ON s.bookingId = b.bookingId " +
		       "WHERE b.roomId = :roomId AND b.bookingDate = :date AND b.status = 'ACTIVE'")
		List<String> findBookedSlots(@Param("roomId") Integer roomId, @Param("date") LocalDate date);
	
	
	@Query(value = "SELECT b.booking_id as bookingId, b.booking_date as bookingDate, " +
	               "b.subject as subject, r.room_no as roomNo, b.status as status " +
	               "FROM bookings b " +
	               "JOIN room_details r ON b.room_id = r.room_id " +
	               "WHERE b.booking_by = :empId AND b.status = 'ACTIVE'", 
	       nativeQuery = true)
	List<Map<String, Object>> findMyActiveBookings(@Param("empId") Long empId);
}	
