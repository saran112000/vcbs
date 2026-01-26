package com.bookings.vcbs.booking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;


@Data
public class BookingRequestDTO {
    private Long roomId;
    private LocalDate bookingDate;
    private String guest;
    private String subject;
   
    private Long empID;
    private String userName;
    private List<String> Slot;
    
    
	@Override
	public String toString() {
		return "BookingRequestDTO [roomId=" + roomId + ", bookingDate=" + bookingDate + ", guest=" + guest
				+ ", subject=" + subject + ", empID=" + empID + ", userName=" + userName + ", Slot=" + Slot + "]";
	}
    
    
}
