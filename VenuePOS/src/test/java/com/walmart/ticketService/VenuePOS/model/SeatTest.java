package com.walmart.ticketService.VenuePOS.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SeatTest {
	
	private Level l1;
	private Level l2;
	
	@Before
	public void setUp() {
		l1 = new Level(1, "TestLevel", 50, 50, new BigDecimal(100));
		l2 = new Level(2, "TestLevel2", 20, 30, new BigDecimal(100));
	}

	@Test
	public void testDuplicateSeat() {
		Seat s1 = new Seat(l1, 10, 50);
		Seat s2 = new Seat(l1, 10, 50);
		assertEquals("Validating that 2 seats are identical",s1, s2);
	}
	
	@Test
	public void testSameSeatDiffLevels() {
		Seat s1 = new Seat(l1, 10, 50);
		Seat s2 = new Seat(l2, 10, 50);
		assertNotEquals("Validating that 2 seats are not identical on different levels",s1, s2);
	}
	
	@Test
	public void testSeatScoreSameLevelDifferentRows() {
		Optional<Seat> s1 = l1.getSeat(1, 50);
		Optional<Seat> s2 = l1.getSeat(2,1);
		assertThat("Seat Score",
		           s1.get().getSeatScore(),
		           lessThan(s2.get().getSeatScore()));
	}
	
	@Test
	public void testSeatScoreSameLevelSameRow() {
		Optional<Seat> s1 = l1.getSeat(1, 1);
		Optional<Seat> s2 = l1.getSeat(1, 25);
		assertThat("Seat Score",
		           s1.get().getSeatScore(),
		           lessThan(s2.get().getSeatScore()));
	}

}
