package com.bookings.vcbs.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookings.vcbs.booking.projection.BookingRoomProjection;
import com.bookings.vcbs.master.modal.RoomDetail;


@Repository
public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {

	@Query(value ="SELECT room_no as roomNo , room_id  as roomId, room_type_id as roomTypeId,is_active as isActive FROM room_details WHERE is_active = 1", nativeQuery = true)
	List<BookingRoomProjection> findActiveRooms();
	
	
	
	@Query(value = "SELECT room_no as roomNo, room_id as roomId, room_type_id as roomTypeId, is_active as isActive " +
            "FROM room_details WHERE room_type_id = :typeId AND is_active = 1", 
    nativeQuery = true)
		List<BookingRoomProjection> findByRoomTypeId(@Param("typeId") Integer typeId);
}

