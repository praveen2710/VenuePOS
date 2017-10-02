package com.walmart.ticketService.VenuePOS.controller.entity;

/**
 * This maps to user's request to make a hold on the seats
 * @author PB033954
 *
 */
public class SeatHoldRequest {

	private int numOfSeats;
	private String customerEmail;
	private Integer minLevel;
	private Integer maxLevel;
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
	public Integer getMinLevel() {
		return minLevel;
	}
	public void setMinLevel(Integer minLevel) {
		this.minLevel = minLevel;
	}
	public Integer getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	@Override
	public String toString() {
		return "SeatHoldRequest [numOfSeats=" + numOfSeats + ", customerEmail=" + customerEmail + ", minLevel="
				+ minLevel + ", maxLevel=" + maxLevel + "]";
	}
	
	
}
