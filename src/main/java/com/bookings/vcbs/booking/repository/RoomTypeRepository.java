package com.bookings.vcbs.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.bookings.vcbs.booking.projection.BookingRoomTypeProjection;
import com.bookings.vcbs.master.modal.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
	
	@Query(value ="SELECT room_type_id as roomTypeId , room_type  as roomType, is_active as isActive FROM room_type WHERE is_active = 1", nativeQuery = true)
	List<BookingRoomTypeProjection> findActiveRoomsType();

}
