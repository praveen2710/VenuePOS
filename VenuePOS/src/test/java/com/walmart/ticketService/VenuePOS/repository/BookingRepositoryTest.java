package com.walmart.ticketService.VenuePOS.repository;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.walmart.ticketService.VenuePOS.model.Level;
import com.walmart.ticketService.VenuePOS.model.Seat;
import com.walmart.ticketService.VenuePOS.model.SeatHold;
import com.walmart.ticketService.VenuePOS.model.VenueConfiguration;

public class BookingRepositoryTest {

	Set<Seat> seats;
	SeatHold sh;
	BookingRepository br;
	int sizeB4;
	private static final int EMPTY = 0;
	
    @Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp() {
		Level l1 = new Level(1, "testDummy",10,5,new BigDecimal(10));
		seats = new HashSet<>();
		Seat s1 = new Seat(l1,1,1);
		Seat s2 = new Seat(l1,1,2);
		Seat s3 = new Seat(l1,1,3);
		Seat s4 = new Seat(l1,1,4);
		seats.add(s1);
		seats.add(s2);
		seats.add(s3);
		seats.add(s4);
		sh = new SeatHold("test@email", seats);
		br = new BookingRepository();
		sizeB4 = br.getHoldingQueue().size();
	}
	
	@Test
	public void testAddSeatOnHoldValid() {
		br.addToHolding(Optional.of(sh));
		assertEquals("HoldingQueueSize Increased",++sizeB4,br.getHoldingQueue().size());
	}
		
	@Test 
	public void testAddSeatOnHoldEmpty() {
		br.addToHolding(Optional.empty());
		assertEquals("HoldingQueueSize Same",sizeB4,br.getHoldingQueue().size());
	}
	
	@Test
	public void testRetrieveFromHoldingQueueValid() {
		br.addToHolding(Optional.of(sh));
		Optional<SeatHold> retrieved = br.retrieveForConfirmation(sh.getSeatHoldId(),sh.getCustomerEmail());
		assertEquals("Email Address Should Match",sh.getCustomerEmail(),retrieved.get().getCustomerEmail());
		assertEquals("Seats Should Match",sh.getHeldSeats(),retrieved.get().getHeldSeats());
	}
	
	@Test
	public void testRetrieveFromHoldingQueueValidIdInvalidEmail() {
		br.addToHolding(Optional.of(sh));
		Optional<SeatHold> retrieved = br.retrieveForConfirmation(sh.getSeatHoldId(),"BadEmail@mail");
		assertEquals("Result Should be Empty due to bad email Id",Optional.empty(),retrieved);
	}
	
	@Test
	public void testRetrieveFromHoldingQueueEmpty() {
		br.addToHolding(Optional.empty());
		Optional<SeatHold> retrieved = br.retrieveForConfirmation(sh.getSeatHoldId(),sh.getCustomerEmail());
		assertEquals("Result Should be Empty",Optional.empty(),retrieved);
	}
	
	@Test
	public void testConfirmReservationValid() {
		br.addToHolding(Optional.of(sh));
		int sizeBeforeBooking = br.getBookedQueue().size();
		Optional<SeatHold> retrieved = br.retrieveForConfirmation(sh.getSeatHoldId(),sh.getCustomerEmail());
		boolean isConfirmed = br.confirmReservation(retrieved.get());
		assertTrue(isConfirmed);
		assertEquals("Booking Confirmed should be incremented by 1",++sizeBeforeBooking,br.getBookedQueue().size());
	}
	
	@Test
	public void testConfirmReservationNull() {
		boolean isConfirmed = br.confirmReservation(null);
		assertFalse(isConfirmed);
	}
	
	@Test
	public void testClearExpiredHoldValid() {
		br.addToHolding(Optional.of(sh));
		int sizeBeforeClearingHolds = br.getHoldingQueue().size();
		//setting hold time low to execute test swiftly
		VenueConfiguration.setVENUERESERVATIONHOLDTIME(1);
		try {
			Thread.sleep(VenueConfiguration.getVENUERESERVATIONHOLDTIME()*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br.clearExpiredHoldSeats();
		assertEquals("HoldingQueueSize should decrease",--sizeBeforeClearingHolds,br.getHoldingQueue().size());
		
	}
	
	@Test
	public void testClearExpiredHoldEmptyQueue() {
		br.addToHolding(Optional.empty());
		VenueConfiguration.setVENUERESERVATIONHOLDTIME(1);
		try {
			Thread.sleep(VenueConfiguration.getVENUERESERVATIONHOLDTIME()*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br.clearExpiredHoldSeats();
		assertEquals("HoldingQueueSize should decrease",EMPTY,br.getHoldingQueue().size());
	}
	
	@Test
	public void testUpdatingHoldQueuesFromGetters() {
		thrown.expect(UnsupportedOperationException.class);
		br.getHoldingQueue().add(sh);
	}
	
	@Test
	public void testUpdatingBookQueuesFromGetters() {
		thrown.expect(UnsupportedOperationException.class);
		br.getBookedQueue().add(sh);
	}
	

}
