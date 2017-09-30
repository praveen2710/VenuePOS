package com.walmart.ticketService.VenuePOS.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;

public class LevelTest {

	@Test
	public void testRowCountInLevel() {
		Level l1 = new Level(1, "TestLevel", 50, 50, new BigDecimal(100));
		 assertEquals("Seat Count as expected",50*50, l1.getSeats().size());
	}
	
	@Test
	public void testDuplicateLevels() {
		Level l1 = new Level(1, "TestLevel", 50, 50, new BigDecimal(100));
		Level l2 = new Level(1, "TestLevel2", 25, 35, new BigDecimal(200));
		assertTrue(l1.equals(l2));
	}
	
	@Test
	public void testNonExistSeatRetriveal() {
		Level l1 = new Level(1, "TestLevel", 50, 50, new BigDecimal(100));
		Optional<Seat> seatInLevel = l1.getSeat(70, 20);
		assertNotNull(seatInLevel);
		assertFalse(seatInLevel.isPresent());
	}
	
	@Test
	public void testValidSeatRetriveal() {
		Level l1 = new Level(1, "TestLevel", 50, 50, new BigDecimal(100));
		Optional<Seat> seatInLevel = l1.getSeat(50, 20);
		assertNotNull(seatInLevel);
		assertTrue(seatInLevel.isPresent());
	}

}
