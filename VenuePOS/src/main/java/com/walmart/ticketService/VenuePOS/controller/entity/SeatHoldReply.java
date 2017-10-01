package com.walmart.ticketService.VenuePOS.controller.entity;

import java.util.Set;

import com.walmart.ticketService.VenuePOS.model.Seat;

public class SeatHoldReply {
	
	private long seatHoldId;
	private final Set<SeatDetails> heldSeats;
	
	public SeatHoldReply(long seatHoldId, Set<SeatDetails> heldSeats) {
		super();
		this.seatHoldId = seatHoldId;
		this.heldSeats = heldSeats;
	}
	
	public long getSeatHoldId() {
		return seatHoldId;
	}

	public Set<SeatDetails> getHeldSeats() {
		return heldSeats;
	}

	@Override
	public String toString() {
		return "SeatHoldReply [seatHoldId=" + seatHoldId + ", heldSeats=" + heldSeats + "]";
	}
	
	
}
