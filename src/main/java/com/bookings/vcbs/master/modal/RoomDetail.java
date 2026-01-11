package com.bookings.vcbs.master.modal;

import java.time.LocalDateTime;

import com.bookings.vcbs.booking.modal.Bookings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "room_details")
@Data
	public class RoomDetail {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "room_id")
	    private Long room_id ;

	    @Column(name = "room_no")
	    private String room_no;

	    @Column(name = "room_type_id")
	    private Long room_type_id;

	    @Column(name = "is_active")
	    private Integer is_active;
	    
	    @Column(name = "created_by", length = 100)
	    private String createdBy;

	    @Column(name = "created_date")
	    private LocalDateTime createdDate;

	    @Column(name = "modified_by", length = 100)
	    private String modifiedBy;

	    @Column(name = "modified_date")
	    private LocalDateTime modifiedDate;
	}
	



