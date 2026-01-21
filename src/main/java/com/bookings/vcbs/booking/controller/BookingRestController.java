package com.bookings.vcbs.booking.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookings.vcbs.booking.dto.BookingRequestDTO;
import com.bookings.vcbs.booking.repository.RoomDetailRepository;
import com.bookings.vcbs.booking.service.BookingService;
import com.bookings.vcbs.master.modal.RoomDetail;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api")
public class BookingRestController {

   

}

	 
//	 @RequestMapping("/api/booked-slots")
//	 public String getBookedSlots(
//	         @RequestParam("roomNo") String room_no,
//	         @RequestParam("bookingDate") String booking_date) {
//		    String a="skjdjh";
//		 
//			System.out.println("room_no****"+room_no);
//			System.out.println("booking_date****"+booking_date);
//	     return a;
//	 }
//	 
//	 
//	 @GetMapping("/bookings")
//	 public String bookingPage(Model model) {
//
//	     List<RoomDetail> rooms = roomDetailRepository.findActiveRooms();
//	     model.addAttribute("rooms", rooms);
//
//	     return "bookings/bookings"; 
//	 }
	 

