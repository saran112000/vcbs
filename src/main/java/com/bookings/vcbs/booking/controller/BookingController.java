	package com.bookings.vcbs.booking.controller;
	
	import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import com.bookings.vcbs.booking.dto.CancelBookingDTO;
import com.bookings.vcbs.booking.projection.BookingRoomProjection;
import com.bookings.vcbs.booking.projection.BookingRoomTypeProjection;
import com.bookings.vcbs.booking.projection.EmployeeProjection;
import com.bookings.vcbs.booking.repository.BookingRepository;
import com.bookings.vcbs.booking.repository.RoomDetailRepository;
import com.bookings.vcbs.booking.repository.RoomTypeRepository;
import com.bookings.vcbs.booking.service.BookingService;
import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.MainModuleDTO;
import com.bookings.vcbs.master.dto.SubModuleDTO;
import com.bookings.vcbs.master.modal.RoomDetail;
import com.bookings.vcbs.master.service.MasterService;

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
		 
		 @Autowired
			private MasterService masterService;

		
		 @RequestMapping("/bookings")
		 public String welcome(Model model, HttpServletRequest req, HttpSession ses) throws Exception {

			 List<BookingRoomTypeProjection> roomTypes = roomTypeRepository.findActiveRoomsType();
		     model.addAttribute("roomsType", roomTypes);

		     List<String> timeSlots = new ArrayList<>();
		     LocalTime startTime = LocalTime.of(9, 0);
		     LocalTime endTime = LocalTime.of(18, 0);
		     while (startTime.isBefore(endTime)) {
		         LocalTime nextSlot = startTime.plusMinutes(30);
		         timeSlots.add(startTime + " - " + nextSlot);
		         startTime = nextSlot;
		     }
		     model.addAttribute("availableSlots", timeSlots);

		     String role = (String) ses.getAttribute("roleName");
		     Long divisionId = (Long) ses.getAttribute("divisionId");
		     List<EmployeeProjection> employees = bookingService.getFilteredEmployees(role, divisionId);
		     model.addAttribute("employees", employees);

		     BookingRequestDTO defaultBooking = new BookingRequestDTO();
		     
		     defaultBooking.setBookingDate(LocalDate.now());
		     
		     Long sessionEmpId = (Long) ses.getAttribute("empId");
		     defaultBooking.setEmpID(sessionEmpId);

		     if (roomTypes != null && !roomTypes.isEmpty()) {
		         model.addAttribute("defaultRoomTypeId", roomTypes.get(0).getRoomTypeId());
		     }

		     
		     
				List<MainModuleDTO> mainModuleList = masterService.getMainModuleList();
				model.addAttribute("mainModuleList", mainModuleList != null ? mainModuleList : new ArrayList<>());
				
				List<SubModuleDTO> subModuleList = masterService.getSubModuleList();
				model.addAttribute("subModuleList", subModuleList != null ? subModuleList : new ArrayList<>());
				
				
		     model.addAttribute("booking", defaultBooking);
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

		 @GetMapping("/api/booked-slots")
		 @ResponseBody
		 public List<String> getBookedSlots(@RequestParam Integer roomId, @RequestParam String date) {
		     try {
		         LocalDate localDate = LocalDate.parse(date);
		         // This calls a custom query in your repository
		         return roomTypeRepository.findBookedSlots(roomId, localDate);
		     } 
		     catch (Exception e) {
		         return Collections.emptyList();
		     }
		 }
		 

		  
		  @PostMapping("/booking/add")
		  public String saveEmployee( Model model, @ModelAttribute("booking") BookingRequestDTO bookingRequestDTO,  RedirectAttributes redirect,  HttpSession ses ) throws Exception {
		      
		      Long empId = (Long) ses.getAttribute("empId");
		      
		    
		      if (bookingRequestDTO == null) {
		          redirect.addFlashAttribute("errorMessage", "Booking data is missing!");
		          return "redirect:/bookings";
		      }

		      
		      bookingRequestDTO.setEmpID(empId);
		      
		      
		      Object result = bookingService.saveBooking(bookingRequestDTO, empId);
		      
		      if (result != null) {
		          redirect.addFlashAttribute("successMessage", "Booking saved successfully!");
		      } else {
		          redirect.addFlashAttribute("errorMessage", "Something went wrong!");
		      }

		     
		      return "redirect:/bookings";
		  }
		  
		  
		  @RequestMapping("/cancelbooking")
		  public String showCancelPage(Model model, HttpSession ses) {
		      Long empId = (Long) ses.getAttribute("empId");
		      
		      CancelBookingDTO cancelvale = new CancelBookingDTO();
		      
		      model.addAttribute("myBookings", roomTypeRepository.findMyActiveBookings(empId));
		      List<MainModuleDTO> mainModuleList = masterService.getMainModuleList();
				model.addAttribute("mainModuleList", mainModuleList != null ? mainModuleList : new ArrayList<>());
				
				List<SubModuleDTO> subModuleList = masterService.getSubModuleList();
				model.addAttribute("subModuleList", subModuleList != null ? subModuleList : new ArrayList<>());
		      return "bookings/cancelbooking";
		  }

		  @PostMapping("/booking/cancel")
		  public String cancelBooking(@ModelAttribute("cancelRequest") CancelBookingDTO cancelDTO, 
		                              RedirectAttributes redirect, 
		                              HttpSession ses) {
		      
		      Long empId = (Long) ses.getAttribute("empId");
		      
		      try {
		          		          cancelDTO.setEmpID(empId);
		          
		          
		          bookingService.cancelBooking(cancelDTO.getBookingId(), empId);
		          
		          redirect.addFlashAttribute("successMessage", "Booking cancelled successfully!");
		      } catch (Exception e) {
		          redirect.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
		      }

		      return "redirect:/cancelbooking";
		  }
	}