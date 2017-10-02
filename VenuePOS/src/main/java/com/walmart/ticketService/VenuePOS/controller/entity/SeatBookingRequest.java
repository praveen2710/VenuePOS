package com.walmart.ticketService.VenuePOS.controller.entity;

public class SeatBookingRequest {
	private int bookingId;
	private String customerEmail;
		
	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Override
	public String toString() {
		return "SeatBookingRequest [bookingId=" + bookingId + ", customerEmail=" + customerEmail + "]";
	}
	
	
}
