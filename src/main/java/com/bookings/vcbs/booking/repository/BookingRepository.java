
package com.bookings.vcbs.booking.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookings.vcbs.booking.modal.Bookings;
import com.bookings.vcbs.booking.projection.EmployeeProjection;


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
	
	
	@Query(value = "SELECT e.emp_id, e.emp_no, e.emp_name, ed.designation, e.division_id " +
            "FROM employee e " +
            "LEFT JOIN employee_desig ed ON ed.desig_id = e.desig_id " +
            "WHERE e.is_active = '1'", nativeQuery = true)
List<EmployeeProjection> findAllActiveEmployees();

@Query(value = "SELECT e.emp_id as empId, " + 
        "e.emp_no as empNo, " +     
        "e.emp_name as empName, " + 
        "e.division_id as divisionId " + 
        "FROM employee e " +
        "WHERE e.division_id = :divId AND e.is_active = '1'", 
nativeQuery = true)
List<EmployeeProjection> findEmployeesByDivision(@Param("divId") Long divId);






}
