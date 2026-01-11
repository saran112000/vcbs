package com.bookings.vcbs.master.modal;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "room_type")
@Data
	public class RoomType {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "room_type_id")
	    private Long room_type_id ;

	    @Column(name = "room_type")
	    private String room_type;


	    @Column(name = "is_active")
	    private Integer is_active;
	    
	    
	}
	

