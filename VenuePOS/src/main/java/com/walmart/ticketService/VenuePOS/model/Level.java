package com.walmart.ticketService.VenuePOS.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * This defines the details for the different levels in the venue. The lower the level the closer it is to the stage
 * @author PB033954
 *
 */
public class Level {
	private int levelId;
	private String name;
	private int rows;
	private int seatsPerRow;
	private BigDecimal price;
	private ArrayList<Seat> seats = new ArrayList<>();
	
	/**
	 * This will return a copy of list of seats related to level.
	 * Changes made here will not affect the acutual saved list. 
	 * @return
	 */
	public ArrayList<Seat> getSeats() {
		return new ArrayList<Seat>(seats);
	}

	public Level(int levelId, String name, int rows, int seatsPerRow, BigDecimal price) {
		super();
		this.levelId = levelId;
		this.name = name;
		this.rows = rows;
		this.seatsPerRow = seatsPerRow;
		this.price = price;
		generateSeats();
	}
	
	/**
	 * This method will generate seats based on user Input
	 */
	private void generateSeats() {
		for(int i=1;i<=this.rows;i++) {
			for(int j=1;j<=this.seatsPerRow;j++) {
				Seat s = new Seat(this, i, j);
				seats.add(s);
			}
		}
	}
	
	public Optional<Seat> getSeat(int row,int seatNumber) {
		for(Seat s: this.seats) {
			if(s.getRow() == row && s.getNumber() == seatNumber) {
				Optional<Seat> seat = Optional.of(s);
				return seat;
			}
		}
		return Optional.empty();
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int id) {
		this.levelId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getSeatsPerRow() {
		//TODO this is an issue since getter can be used to set data fix later
		return seatsPerRow;
	}

	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Level [id=" + levelId + ", name=" + name + ", rows=" + rows + ", seatsPerRow=" + seatsPerRow + ", Price="
				+ price + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(!(obj instanceof Level)) {
			return false;
		}
		Level l = (Level)obj;
		return l.levelId == this.levelId;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(levelId);
	}

	
}
