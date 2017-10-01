package com.walmart.ticketService.VenuePOS.controller.entity;

public class SeatBookingRequest {
	private int seatHoldId;
	private String customerEmail;
		
	public int getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Override
	public String toString() {
		return "SeatBookingRequest [seatHoldId=" + seatHoldId + ", customerEmail=" + customerEmail + "]";
	}
	
	
}
