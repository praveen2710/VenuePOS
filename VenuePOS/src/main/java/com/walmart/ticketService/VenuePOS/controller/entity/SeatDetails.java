package com.walmart.ticketService.VenuePOS.controller.entity;

import java.math.BigDecimal;

/**
 * Details required to send the user about his reserved seats
 * @author PB033954
 *
 */
public class SeatDetails {
	private int seatRow;
	private int seatNumber;
	private BigDecimal priceOfSeat;
	private String level;
	
	public String getLevel() {
		return level;
	}

	public BigDecimal getPriceOfSeat() {
		return priceOfSeat;
	}

	public int getSeatRow() {
		return seatRow;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public SeatDetails(int seatRow, int seatNumber,BigDecimal price,String level) {
		super();
		this.seatRow = seatRow;
		this.seatNumber = seatNumber;
		this.priceOfSeat = price;
		this.level = level;
	}
	
	
	
}
