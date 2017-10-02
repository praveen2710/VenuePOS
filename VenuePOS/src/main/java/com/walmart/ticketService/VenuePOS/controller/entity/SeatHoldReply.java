package com.walmart.ticketService.VenuePOS.controller.entity;

import java.util.Set;

import com.walmart.ticketService.VenuePOS.model.Seat;

/**
 * This contains the bookingId and {@link SeatDetails}
 * @author PB033954
 *
 */
public class SeatHoldReply {
	
	private long bookingId;
	private final Set<SeatDetails> heldSeats;
	
	public SeatHoldReply(long bookingId, Set<SeatDetails> heldSeats) {
		super();
		this.bookingId = bookingId;
		this.heldSeats = heldSeats;
	}
	
	public long getBookingId() {
		return bookingId;
	}

	public Set<SeatDetails> getHeldSeats() {
		return heldSeats;
	}

	@Override
	public String toString() {
		return "SeatHoldReply [bookingId=" + bookingId + ", heldSeats=" + heldSeats + "]";
	}
	
	
}
