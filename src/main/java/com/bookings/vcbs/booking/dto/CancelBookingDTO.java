package com.bookings.vcbs.booking.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;


@Data
public class CancelBookingDTO {
	
	
    private Long roomId;
    private LocalDate bookingDate;
    private String guest;
    private String subject;
    private Long BookingId;
   
    private Long empID;
    private String userName;
    private String Slot;
    private String Reason;
	@Override
	public String toString() {
		return "CancelBookingDTO [roomId=" + roomId + ", bookingDate=" + bookingDate + ", guest=" + guest + ", subject="
				+ subject + ", empID=" + empID + ", userName=" + userName + ", Slot=" + Slot + ", Reason=" + Reason
				+ "]";
	}
    
    
	

}
