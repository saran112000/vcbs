	package com.bookings.vcbs.booking.controller;
	
	import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookings.vcbs.booking.dto.BookingRequestDTO;
import com.bookings.vcbs.booking.projection.BookingRoomProjection;
import com.bookings.vcbs.booking.projection.BookingRoomTypeProjection;
import com.bookings.vcbs.booking.repository.BookingRepository;
import com.bookings.vcbs.booking.repository.RoomDetailRepository;
import com.bookings.vcbs.booking.repository.RoomTypeRepository;
import com.bookings.vcbs.booking.service.BookingService;
import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.modal.RoomDetail;

import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpSession;
	
	@Controller
	public class BookingController {
		
		@Autowired
		private RoomDetailRepository roomDetailRepository;
		
		@Autowired
		private RoomTypeRepository roomTypeRepository;
		 @Autowired
		    private BookingService bookingService;

		
		
		 @RequestMapping("/bookings")
		    public String welcome(Model model,HttpServletRequest req, HttpSession ses) throws Exception
		    {
		 List<BookingRoomProjection> rooms = roomDetailRepository.findActiveRooms();
		 model.addAttribute("rooms", rooms);

		 rooms.forEach(row-> System.out.println(row));
			 
			 List<BookingRoomTypeProjection> roomType =roomTypeRepository.findActiveRoomsType();
			 model.addAttribute("roomsType", roomType);
			 
			 roomType.forEach(row-> System.out.println(row));
		        return "bookings/bookings";
		    }
		 	
		 
		 
		 @GetMapping("/api/rooms-by-type")
		    @ResponseBody
		    public List<BookingRoomProjection> getRoomsByType(@RequestParam("typeId") Integer typeId) {
		        try {
		            return roomDetailRepository.findByRoomTypeId(typeId);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return Collections.emptyList();
		        }
		    }

		  @PostMapping("/book-slot")
		    public Map<String, String> bookSlot(
		            @RequestBody BookingRequestDTO dto) {

		        System.out.println("BOOK SLOT API HIT");

		        //Long empId = (Long) session.getAttribute("empId"); // ✅ matches LoginController

//		        if (empId == null) {
//		            return Map.of(
//		                "status", "ERROR",
//		                "message", "Session expired. Please login again"
//		            );
//		        }

//		        bookingService.saveBooking(dto, empId);

		        return Map.of("status", "SUCCESS");
		    }
		  
		  
		    @GetMapping("/booking/book")
		    public String employeeList(Model model) {
		    	
		    	
		    	 List<EmployeeDTO> employeeList = masterService.getEmployeeList();
		         model.addAttribute("employeeList", employeeList != null ? employeeList : new ArrayList<>());
		        
		        
		        model.addAttribute("booking", new BookingRequestDTO());
		        return "bookings/bokkings";
		    }

		    @PostMapping("/booking/add")
		    public String saveEmployee(@ModelAttribute BookingRequestDTO BookingRequestDTO, RedirectAttributes redirect, HttpSession ses ) throws Exception {
		        String userName = (String)ses.getAttribute("userName");
		        Long empId = (Long)ses.getAttribute("empId");
		        
		        
		        System.out.println("================-----"+BookingRequestDTO);
		        if(BookingRequestDTO == null) {
		            redirect.addFlashAttribute("errorMessage", "Employee data is missing!");
		            return "redirect:/booking/add";
		        }
		        
		        
		        
		        BookingRequestDTO.setEmpID(empId);
		        List status = (List) bookingService.saveBooking(BookingRequestDTO,empId);
		        
		      
		        if(status != null ) {
		            redirect.addFlashAttribute("successMessage", "booking  successfully!");
		        } else {
		            redirect.addFlashAttribute("errorMessage", "Something went wrong!");
		        }
		        return "redirect:/booking/book";
		    }

	
	}