package com.bookings.vcbs.report.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookings.vcbs.booking.modal.Bookings;
import com.bookings.vcbs.report.projection.BookingDetailProjection;


@Repository
public interface BookingReportRepository extends JpaRepository<Bookings, Long> {

	@Query(value = """
		    SELECT b.booking_id, b.booking_date, b.booking_by, e.emp_name AS slotBookedBy, e.desig_id, ed.designation AS slotBookedByDesignation,
			b.room_id, rd.room_no, rd.room_type_id, rt.room_type, b.guest_name, b.subject, b.status, s.bookedSlots
			FROM bookings b
			LEFT JOIN employee e ON e.emp_id = b.booking_by AND e.is_active = 1
			LEFT JOIN employee_desig ed ON ed.desig_id = e.desig_id
			INNER JOIN room_details rd ON rd.room_id = b.room_id AND rd.is_active = 1
			LEFT JOIN room_type rt ON rt.room_type_id = rd.room_type_id AND rt.is_active = 1
			INNER JOIN (
			    SELECT s.booking_id, GROUP_CONCAT(s.slot_time) AS bookedSlots
			    FROM bookings_slot_details s WHERE s.is_active = 1 GROUP BY s.booking_id
			) s ON s.booking_id = b.booking_id
			WHERE b.status = :status AND (b.booking_date BETWEEN :fromDate AND :toDate)
		""", nativeQuery = true)
		List<BookingDetailProjection> getRoomBookedList(@Param("status") String status, LocalDate fromDate, LocalDate toDate);

}
