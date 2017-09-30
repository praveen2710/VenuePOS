package com.walmart.ticketService.VenuePOS.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class SeatHoldTest {
	
	private Set<Seat> mockSeatsHold;
	private Level l1;
	
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
		
		assertEquals("Size of Set is of Unique Seat Holds only",1,seatHoldSet.size());	

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
		
		assertEquals("Size of Set is of Unique Even if seats are added in diff order",1,seatHoldSet.size());	
	}
	
	@Test
	public void testSeatStatusAfterSeatHold() {
		for(Seat seatBeforeHold:mockSeatsHold) {
			assertEquals("Seat should be available",Status.AVAILABLE,seatBeforeHold.getStatus());
		}
		SeatHold s1 = new SeatHold("Test Email", mockSeatsHold);
		for(Seat seatAfterHold:s1.getHeldSeats()) {
			assertEquals("Seat should be available",Status.HOLD,seatAfterHold.getStatus());
		}
		
	}

}
