package com.walmart.ticketService.VenuePOS.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class LevelTest {
	Level l1;
	
	@Before
	public void setUp() {
		l1 = new Level(1, "TestLevel", 50, 50, new BigDecimal(100));
	}

	@Test
	public void testRowCountInLevel() {
		 assertEquals("Seat Count expected",50*50, l1.getSeats().size());
	}
	
	@Test
	public void testDuplicateLevels() {
		Level l2 = new Level(1, "TestLevel2", 25, 35, new BigDecimal(200));
		assertTrue(l1.equals(l2));
	}
	
	@Test
	public void testNonExistSeatRetriveal() {
		Optional<Seat> seatInLevel = l1.getSeat(70, 20);
		assertNotNull(seatInLevel);
		assertFalse(seatInLevel.isPresent());
	}
	
	@Test
	public void testValidSeatRetriveal() {
		Optional<Seat> seatInLevel = l1.getSeat(50, 20);
		assertNotNull(seatInLevel);
		assertTrue(seatInLevel.isPresent());
	}
	
	@Test 
	public void testDeleteSeatFromGetters() {
		int sizeBeforeRemoval = l1.getSeats().size();
		l1.getSeats().remove(1);
		assertEquals("No Of Seats should not change",sizeBeforeRemoval,l1.getSeats().size());
	}

}
