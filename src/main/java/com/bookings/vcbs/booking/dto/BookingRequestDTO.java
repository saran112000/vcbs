package com.bookings.vcbs.booking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;


@Data
public class BookingRequestDTO {
    private Long roomId;
    private LocalDate bookingDate;
    private String guest;
    private String subject;
    private String slotTime;
    private Long empID;
    private String userName;
}
