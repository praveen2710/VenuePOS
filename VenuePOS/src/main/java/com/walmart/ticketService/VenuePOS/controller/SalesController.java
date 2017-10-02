package com.walmart.ticketService.VenuePOS.controller;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
	private VenueConfiguration vc;
	
	public SalesController() {
		Level l1  = new Level(1,"Orchestra",25,50,new BigDecimal(100));
		Level l2  = new Level(2,"Main",20,100,new BigDecimal(75));
		Level l3  = new Level(3,"Balcony 1",15,100,new BigDecimal(50));
		Level l4  = new Level(4,"Balcony 2",15,100,new BigDecimal(40));
		 		
		Set<Level> levels = new HashSet<>();
		levels.add(l1);
		levels.add(l2);
		levels.add(l3);
		levels.add(l4);
 		vc = new VenueConfiguration("Chicago Symphony Center",levels);
		BookingRepository br = new BookingRepository();
		tsl= new TicketServiceImpl(vc,br);
	}
	
	/**
	 * Welcome page
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage() {
		return "Welcome to "+vc.getName()+" Booking System";
	}
		
	/**
	 * This will calculate the empty seats in the complete venue
	 * @return count of empty seats
	 */
	@RequestMapping(value = "emptySeats", method = RequestMethod.GET)
	public int listAvailableSeats() {
		return tsl.numSeatsAvailable(null);
	}
	
	/**
	 * This will calculate the the count of empty seats at a particular level
	 * @param levelId
	 * @return count of empty seats
	 */
	@RequestMapping(value = "emptySeats/{levelId}", method = RequestMethod.GET)
	public int listAvailableSeatsInLevel(@PathVariable Optional<Integer> levelId) {
		return tsl.numSeatsAvailable(levelId);
	}
	
	/**
	 * This will take care of putting seats on hold for customer
	 * @param seatHoldRequest
	 * @return
	 */
	@RequestMapping(value = "seats/hold", method = RequestMethod.POST)
	public ResponseEntity<SeatHoldReply> holdSeatsIfAvailable(@RequestBody SeatHoldRequest seatHoldRequest) {
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
	
	/**
	 * This will handle reservation of the seats that are being held for customer
	 * @param seatBookingRequest
	 * @return
	 */
	@RequestMapping(value = "seats/reserve", method = RequestMethod.POST)
	public ResponseEntity<String> confirmHeldSeatsIfValid(@RequestBody SeatBookingRequest seatBookingRequest) {		String bookingReply = tsl.reserveSeats(seatBookingRequest.getBookingId(), seatBookingRequest.getCustomerEmail());
		ResponseEntity<String> response; 
	
		if(bookingReply.equals(TicketServiceImpl.BOOKING_SUCCESS)) {
			response =  new ResponseEntity<>("Booking Confirmed",HttpStatus.OK);
		}else if(bookingReply.equals(TicketServiceImpl.BOOKING_DUPLICATE)) {
			response =  new ResponseEntity<>("Booking has been reserved already",HttpStatus.OK);
		}else {
			response = new ResponseEntity<>("Booking could not be found to reserve",HttpStatus.NOT_FOUND);
		}
		return response;
	}	
}
