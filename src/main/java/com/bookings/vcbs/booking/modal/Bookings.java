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
@Table(name = "bookings")
@Data
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "booking_by", length = 100)
    private Long bookingBy;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "subject", length = 500)
    private String subject;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by", length=20)
    private Long createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_by" , length = 100)
    private String  modified_by;

    @Column(name = "modified_date")
    private LocalDateTime modifieDate;

	

   
}
