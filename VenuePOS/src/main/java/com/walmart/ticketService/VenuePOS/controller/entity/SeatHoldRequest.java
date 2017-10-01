package com.walmart.ticketService.VenuePOS.controller.entity;

public class SeatHoldRequest {

	private int numOfSeats;
	private String customerEmail;
	private int minLevel;
	private int maxLevel;
	public int getNumOfSeats() {
		return numOfSeats;
	}
	public void setNumOfSeats(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public int getMinLevel() {
		return minLevel;
	}
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	@Override
	public String toString() {
		return "SeatHoldRequest [numOfSeats=" + numOfSeats + ", customerEmail=" + customerEmail + ", minLevel="
				+ minLevel + ", maxLevel=" + maxLevel + "]";
	}
	
	
}
