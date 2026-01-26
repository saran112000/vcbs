package com.bookings.vcbs.booking.modal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "cancelled_bookings")
@Data
public class CancelBooking {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
    private Long cancelId;
    
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "cancellation_date")
    private LocalDate cancellationDate;

    @Column(name = "reason", length = 100)
    private String reason;


    @Column(name = "cancelled_by")
    private Long cancelledBy;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

	

   
}

