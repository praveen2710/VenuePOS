package com.walmart.ticketService.VenuePOS.model;

import java.util.Objects;

/**
 * This class defines characteristic of a seat in a venue 
 * 
 */
public class Seat {
	private int row;
	private int seatNumber;
	private Level level;
	private Status status = Status.AVAILABLE;
	private int seatScore;

	public Seat(Level level,int row,int number) {
		this.level = level;
		this.seatNumber = number;
		this.row = row;
		calculateSeatScore();
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getNumber() {
		return seatNumber;
	}

	public void setNumber(int number) {
		this.seatNumber = number;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * This will prevent user from adding new seats that are already existing
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==this) {
			return true;
		}
		if(!(obj instanceof Seat)) {
			return false;
		}
		Seat s = (Seat) obj;
		return s.seatNumber == this.seatNumber && s.row == this.row && s.level == this.level;
	}

	@Override
	public int hashCode() {
		return Objects.hash(seatNumber,row,level);
	}
	
	public float getSeatScore() {
		return seatScore;
	}
	
	/**
	 * Formula to find the score of seat . row 1 seat 1 is better than row 1 seat 25.
	 * Similarly row 2 seat 1 has lower score than row 1 seat 25
	 */
	private void calculateSeatScore() {
		int relSeatPos = level.getSeatsPerRow() - ( level.getSeatsPerRow() - this.seatNumber);
		int rowOffset = 0;
		for(int i = 1; i < this.row ; i++) {
			rowOffset += i * level.getSeatsPerRow();
		}
		this.seatScore = relSeatPos + rowOffset;
		
	}

	@Override
	public String toString() {
		return "Seat [row=" + row + ", seatNumber=" + seatNumber + ", level=" + level + ", status=" + status
				+ ", seatScore=" + seatScore + "]";
	}

}
