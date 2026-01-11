package com.bookings.vcbs.booking.modal;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bookings_slot_details")
@Data
public class BookingsSlotDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private Long slotId;

    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "slot_time", length = 100)
    private String slotTime;

    @Column(name = "cancelled_date")
    private LocalDateTime cancelledDate;

    @Column(name = "cancelled_by")
    private Long cancelleBy;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "created_by", length=20)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createDate;

    @Column(name = "modified_by", length = 100)
    private String modifieBy;

    @Column(name = "modified_date")
    private LocalDateTime modifieDate;

   
}
