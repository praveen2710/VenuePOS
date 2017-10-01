package com.walmart.ticketService.VenuePOS.repository;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.walmart.ticketService.VenuePOS.model.SeatHold;
import com.walmart.ticketService.VenuePOS.model.Status;
import com.walmart.ticketService.VenuePOS.model.VenueConfiguration;
import com.walmart.ticketService.VenuePOS.service.TicketServiceImpl;

/**
 * This will hold the information about which seats are being held and are reserved for customer's
 * @author PB033954
 *
 */
public class BookingRepository {

	private Set<SeatHold> holdingQueue;
	private Set<SeatHold> bookedQueue;
	
	static Logger LOGGER = LoggerFactory.getLogger(BookingRepository.class);

	public BookingRepository() {
		holdingQueue = new HashSet<>();
		bookedQueue = new HashSet<>();
	}
	
	/**
	 * This will put the seats held for later to be booked
	 * @param sh : {@link SeatHold} seats to hold for customer
	 */
	public void addToHolding(Optional<SeatHold> sh) {
		sh.ifPresent(heldSeat -> holdingQueue.add(heldSeat));
	}
	
	/**
	 * This will retrieve the reservation that is being held
	 * @param seatHoldBookingId to retrieve the booking hold
	 * @param customerEmail email address associated to booking
	 * @return SeatHold details on the seats being held
	 */
	public Optional<SeatHold> retrieveForConfirmation(int seatHoldBookingId, String customerEmail) {
		return holdingQueue.stream().filter(holding -> holding.getSeatHoldId() == seatHoldBookingId && holding.getCustomerEmail().equals(customerEmail)).findFirst();
	}
	
	/**
	 * This will confirm the existing reservation that is held and not expired
	 * @param booking {@link SeatHold} reservation that needs to be confirmed
	 * @return true if booking was confirmed false otherwise
	 */
	public boolean confirmReservation(SeatHold booking) {
		if(holdingQueue.contains(booking)) {
			booking.getHeldSeats().forEach(s-> s.setStatus(Status.RESERVED));
			holdingQueue.remove(booking);
			bookedQueue.add(booking);		
			return true;
		}
		return false;
	}
	
	public boolean retrieveBookedSeats(int seatHoldId,String customerEmail){
		return bookedQueue.stream().anyMatch(booking -> booking.getSeatHoldId() == seatHoldId && booking.getCustomerEmail().equals(customerEmail));
	}
	
	/**
	 * This method will clear out holds on seats if there have been booked over 
	 * hold time configured for venue in {@link VenueConfiguration}
	 */
	public void clearExpiredHoldSeats() {
		holdingQueue.removeIf(s -> {
			Duration d = Duration.between(s.getReservationTime(),Instant.now());
			
			if(d.get(ChronoUnit.SECONDS)>= VenueConfiguration.getVENUERESERVATIONHOLDTIME()) {
				s.getHeldSeats().forEach(seat->seat.setStatus(Status.AVAILABLE));
				LOGGER.info("clearExpiredHoldSeats:" + s.toString());
			}
			return d.get(ChronoUnit.SECONDS)>= VenueConfiguration.getVENUERESERVATIONHOLDTIME();		
		});
	}
	
	//TODO implement defensive coding
	public Set<SeatHold> getBookedQueue() {
		return Collections.unmodifiableSet(this.bookedQueue);
	}
	
	//TODO implement defensive coding
	public Set<SeatHold> getHoldingQueue() {
		return Collections.unmodifiableSet(this.holdingQueue);
	}

	
}
