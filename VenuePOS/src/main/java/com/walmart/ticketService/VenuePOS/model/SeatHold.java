package com.walmart.ticketService.VenuePOS.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * Details of Seats that are being held for customer.
 * Reservation time and seats held should not be modifiable
 * @author PB033954
 *
 */
public class SeatHold {

	private String customerEmail;
	private Instant reservationTime;
	private final Set<Seat> heldSeats;
	private int seatHoldId;
	
	public SeatHold(String customerEmail, Set<Seat> heldSeats) {
		super();
		this.customerEmail = customerEmail;
		this.heldSeats = heldSeats;
		this.seatHoldId = this.hashCode();
		heldSeats.forEach(s -> s.setStatus(Status.HOLD));
		this.reservationTime = Instant.now();
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void updateCustomerEmail(String newEmailAddress) {
		this.customerEmail = newEmailAddress;
	}

	public Instant getReservationTime() {
		return reservationTime;
	}
	
	public Set<Seat> getHeldSeats() {
		return Collections.unmodifiableSet(heldSeats);
	}

	public int getSeatHoldId() {
		return seatHoldId;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(!(obj instanceof SeatHold)){
			return false;
		}
		SeatHold sh = (SeatHold) obj;
		return sh.heldSeats.equals(this.heldSeats);
	}

	@Override
	public int hashCode() {
		return heldSeats.hashCode();
	}

	@Override
	public String toString() {
		return "SeatHold [reservationTime=" + reservationTime + ", seatHoldId=" + seatHoldId + "]";
	}

	
		
}
