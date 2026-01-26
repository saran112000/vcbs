package com.bookings.vcbs.report.projection;

import java.time.LocalDate;

public interface BookingDetailProjection {

    // b.booking_id
    Long getBookingId();

    // b.booking_date
    LocalDate getBookingDate();

    // b.booking_by
    Long getBookingBy();

    // e.emp_name AS slotBookedBy
    String getSlotBookedBy();

    // e.desig_id
    Long getDesigId();

    // ed.designation AS slotBookedByDesignation
    String getSlotBookedByDesignation();

    // b.room_id
    Long getRoomId();

    // rd.room_no
    String getRoomNo();

    // rd.room_type_id
    Long getRoomTypeId();

    // rt.room_type
    String getRoomType();

    // b.guest_name
    String getGuestName();

    // b.subject
    String getSubject();

    // b.status
    String getStatus();

    // s.bookedSlots (result of GROUP_CONCAT)
    String getBookedSlots();
}