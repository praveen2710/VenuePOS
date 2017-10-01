package com.walmart.ticketService.VenuePOS.controller;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.ticketService.VenuePOS.controller.entity.SeatBookingRequest;
import com.walmart.ticketService.VenuePOS.controller.entity.SeatDetails;
import com.walmart.ticketService.VenuePOS.controller.entity.SeatHoldReply;
import com.walmart.ticketService.VenuePOS.controller.entity.SeatHoldRequest;
import com.walmart.ticketService.VenuePOS.model.Level;
import com.walmart.ticketService.VenuePOS.model.Seat;
import com.walmart.ticketService.VenuePOS.model.SeatHold;
import com.walmart.ticketService.VenuePOS.model.VenueConfiguration;
import com.walmart.ticketService.VenuePOS.repository.BookingRepository;
import com.walmart.ticketService.VenuePOS.service.TicketServiceImpl;

@RestController
@RequestMapping("api/v1/")
public class SalesController {
	
	private TicketServiceImpl tsl;
	
	public SalesController() {
		System.out.println("Setup mock data and service here");
		Level l1  = new Level(1,"Orchestra",25,50,new BigDecimal(100));
		Level l2  = new Level(2,"Main",20,100,new BigDecimal(75));
		Level l3  = new Level(3,"Balcony 1",15,100,new BigDecimal(50));
		Level l4  = new Level(4,"Balcony 2",15,100,new BigDecimal(40));
		 		
		Set<Level> levels = new HashSet<>();
		levels.add(l1);
		levels.add(l2);
		levels.add(l3);
		levels.add(l4);
 		VenueConfiguration vc = new VenueConfiguration("Chicago Symphony Center",levels);
		BookingRepository br = new BookingRepository();
		tsl= new TicketServiceImpl(vc,br);
	}
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String testHomePage() {
		return "Home sweet home";
	}
	
	@RequestMapping(value = "emptySeats", method = RequestMethod.GET)
	public int listAvailableSeats() {
		return tsl.numSeatsAvailable(null);
	}
	
	@RequestMapping(value = "emptySeats/{levelId}", method = RequestMethod.GET)
	public int listAvailableSeatsInLevel(@PathVariable Optional<Integer> levelId) {
		//TODO exception handling and logging
		return tsl.numSeatsAvailable(levelId);
	}
	
	@RequestMapping(value = "seats/hold", method = RequestMethod.POST)
	public ResponseEntity<SeatHoldReply> holdSeatsIfAvailable(@RequestBody SeatHoldRequest seatHoldRequest) {
		//TODO exception handling and logging
		SeatHold se = tsl.findAndHoldSeats(seatHoldRequest.getNumOfSeats()
									,Optional.ofNullable(seatHoldRequest.getMinLevel())
									,Optional.ofNullable(seatHoldRequest.getMaxLevel())
									,seatHoldRequest.getCustomerEmail());
		ResponseEntity<SeatHoldReply> response;
		if(se!=null) {
			Set<SeatDetails> seatsHeld = new HashSet<>();
			for(Seat seat:se.getHeldSeats()) {
				seatsHeld.add(new SeatDetails(seat.getRow(),seat.getNumber(),seat.getLevel().getPrice(),seat.getLevel().getName()));
			}
			response = new ResponseEntity<>(new SeatHoldReply(se.getSeatHoldId(),seatsHeld), HttpStatus.OK);
		}else {
			response = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return response;
	}
	
	@RequestMapping(value = "seats/reserve", method = RequestMethod.POST)
	public ResponseEntity<String> confirmHeldSeatsIfValid(@RequestBody SeatBookingRequest seatBookingRequest) {
		//TODO exception handling and logging
		String bookingReply = tsl.reserveSeats(seatBookingRequest.getSeatHoldId(), seatBookingRequest.getCustomerEmail());
		
		ResponseEntity<String> response; 
		
		if(bookingReply.equals(TicketServiceImpl.BOOKING_SUCCESS)) {
			response =  new ResponseEntity<>("Booking Confirmed",HttpStatus.OK);
		}else if(bookingReply.equals(TicketServiceImpl.BOOKING_DUPLICATE)) {
			response =  new ResponseEntity<>("Booking has been confirmed already",HttpStatus.OK);
		}else {
			response = new ResponseEntity<>("Reservation could not be found to confirm",HttpStatus.NOT_FOUND);
		}
		return response;
	}	
}
