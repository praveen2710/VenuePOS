package com.walmart.ticketService.VenuePOS.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SeatHoldTest {
	
	private Set<Seat> mockSeatsHold;
	private Level l1;
	
    @Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp() {
		mockSeatsHold = new HashSet<>();
		l1 = new Level(1,"TestLevel",10, 10,new BigDecimal(10));
		mockSeatsHold.add(l1.getSeat(1, 1).get());
		mockSeatsHold.add(l1.getSeat(1, 2).get());
		mockSeatsHold.add(l1.getSeat(1, 3).get());
		mockSeatsHold.add(l1.getSeat(1, 4).get());
	}

	@Test
	public void testSeatHoldWithDuplicate() {
		SeatHold s1 = new SeatHold("Test Email", mockSeatsHold);
		
		Set<Seat> mockSeatsHoldSameOrder = new HashSet<>();
		mockSeatsHoldSameOrder.add(l1.getSeat(1, 1).get());
		mockSeatsHoldSameOrder.add(l1.getSeat(1, 2).get());
		mockSeatsHoldSameOrder.add(l1.getSeat(1, 3).get());
		mockSeatsHoldSameOrder.add(l1.getSeat(1, 4).get());
	
		SeatHold s2 = new SeatHold("Test Email2", mockSeatsHoldSameOrder);
		assertEquals("Both Seat Holds are identical Same Order",s1,s2);
		
		Set<SeatHold> seatHoldSet = new HashSet<>();
		seatHoldSet.add(s1);
		seatHoldSet.add(s2);
		
		assertEquals("Size of Set should be of unique holds only",1,seatHoldSet.size());	

	}
	
	@Test
	public void testSeathHoldWithDuplicateDiffOrderSet() {
		
		SeatHold s1 = new SeatHold("Test Email", mockSeatsHold);
		
		Set<Seat> mockSeatsHoldDiffOrder = new HashSet<>();
		mockSeatsHoldDiffOrder.add(l1.getSeat(1, 4).get());
		mockSeatsHoldDiffOrder.add(l1.getSeat(1, 2).get());
		mockSeatsHoldDiffOrder.add(l1.getSeat(1, 1).get());
		mockSeatsHoldDiffOrder.add(l1.getSeat(1, 3).get());
		
		SeatHold s3 = new SeatHold("Test Email 3", mockSeatsHoldDiffOrder);
		
		Set<SeatHold> seatHoldSet = new HashSet<>();
		seatHoldSet.add(s1);
		seatHoldSet.add(s3);
		
		assertEquals("Size of Set should be of unique holds only even if insertion order is different",1,seatHoldSet.size());	
	}
	
	@Test
	public void testSeatStatusAfterSeatHold() {
		for(Seat seatBeforeHold:mockSeatsHold) {
			assertEquals("Seat should be available",Status.AVAILABLE,seatBeforeHold.getStatus());
		}
		SeatHold s1 = new SeatHold("Test Email", mockSeatsHold);
		for(Seat seatAfterHold:s1.getHeldSeats()) {
			assertEquals("Seat should be on hold",Status.HOLD,seatAfterHold.getStatus());
		}
		
	}
	
	@Test
	public void testAlterSeatsAfterHolding() {
		SeatHold s1 = new SeatHold("Test Email", mockSeatsHold);
		thrown.expect(UnsupportedOperationException.class);
		s1.getHeldSeats().remove(l1.getSeat(1, 1).get());		
	}
	
	@Test
	public void testupdateEmailAddressWithCorrectId() {
		SeatHold s1 = new SeatHold("Test Email", mockSeatsHold);
		s1.updateCustomerEmail("New Email");
		assertTrue(s1.getCustomerEmail().equals("New Email"));
	}
	
}
