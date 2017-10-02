package com.walmart.ticketService.VenuePOS.service;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.walmart.ticketService.VenuePOS.customexceptions.SeatsNotFoundException;
import com.walmart.ticketService.VenuePOS.model.Level;
import com.walmart.ticketService.VenuePOS.model.Seat;
import com.walmart.ticketService.VenuePOS.model.SeatHold;
import com.walmart.ticketService.VenuePOS.model.Status;
import com.walmart.ticketService.VenuePOS.model.VenueConfiguration;
import com.walmart.ticketService.VenuePOS.repository.BookingRepository;
import com.walmart.ticketService.VenuePOS.util.TicketServiceUtil;

public class TicketServiceImplTest {
	
	private TicketServiceImpl tsi;
	private VenueConfiguration vc;
	private Level l1,l2,l3,l4;
	private static final int TOTALSEATSINAUDITORIUM = 6250;
	
	
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    
	@Before
	public void setUp() {
		l1  = new Level(1,"Orchestra",25,50,new BigDecimal(100));
		l2  = new Level(2,"Main",20,100,new BigDecimal(75));
		l3  = new Level(3,"Balcony 1",15,100,new BigDecimal(50));
		l4  = new Level(4,"Balcony 2",15,100,new BigDecimal(40));
		
		HashSet<Level> levels = new HashSet<>();
		levels.add(l1);
		levels.add(l2);
		levels.add(l3);
		levels.add(l4);
		vc = new VenueConfiguration("Chicago Symphony Center",levels);
		BookingRepository br = new BookingRepository();
		tsi= new TicketServiceImpl(vc,br);
	}
	
	@Test
	public void testNumOfSeatsAllLevels() {
		int totalCount = tsi.numSeatsAvailable(null);
		assertEquals("Total Count Of Available Seats",TOTALSEATSINAUDITORIUM,totalCount);
	}
	
	@Test
	public void testNumOfAvilableSeatsInLevel() {
		int availableCountInLevel = tsi.numSeatsAvailable(Optional.of(1));
		assertEquals("Total Count Of Available Seats",getAvilableSeatCountInLevel(1),availableCountInLevel);
	}
	
	
	@Test
	public void testNumOfAvilableSeatsInInvalidLevel() {
		thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("The level"));
		tsi.numSeatsAvailable(Optional.of(11));
	}
	

	@Test
	public void testResultWhenNoSeatsAvaialble() {
		l1.getSeats().forEach(s -> s.setStatus(Status.HOLD));
		int availableCountInLevel = tsi.numSeatsAvailable(Optional.of(1));
		assertEquals("Total Count Of Available Seats",0,availableCountInLevel);
	}
	
	@Test 
	public void testFindAndHoldSeatsMinGreaterThanMax() {
		thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("Min Level cannot"));
		tsi.findAndHoldSeats(10,Optional.of(2),Optional.of(1),"Test@email.com");
	}
	
	@Test 
	public void testHoldSeatsAtMinLevelWhenNoLevelMentioned() {
		SeatHold seatsHeld = tsi.findAndHoldSeats(10,null,null,"Test@email.com");
		assertEquals("No Of Seats Held",10,seatsHeld.getHeldSeats().size());
		//Ensure all seats are on same level
		for(Seat seat:seatsHeld.getHeldSeats()) {
			assertEquals("All Seats are to be in same level",TicketServiceUtil.getMinVenueLevel(vc),seat.getLevel().getLevelId());
		}
	}
	
	@Test
	public void testHoldSeatsAtNextLevelWhenNoEnoughSeatsInMinLevel() {
		//Leaving less than required seats in level 1
		mockReserveSeats(l1,8);
		SeatHold seatsHeld = tsi.findAndHoldSeats(10,null,null,"Test@email.com");
		assertEquals("No Of Seats Held",10,seatsHeld.getHeldSeats().size());
		//all seats must be in level 2
		for(Seat seat:seatsHeld.getHeldSeats()) {
			assertEquals("All Seats are to be in same level",2,seat.getLevel().getLevelId());
		}
	}
	
	
	@Test
	public void testHoldSeatsWhenInvalidMinLevels() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("Min level"));
		tsi.findAndHoldSeats(11,Optional.of(15), Optional.of(3),"Test@email.com");
	}
	
	@Test
	public void testHoldSeatsWhenInvalidMaxLevels() {	
		thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("Max level"));
		tsi.findAndHoldSeats(12, Optional.of(1), Optional.of(15), "Test@email.com");
	}
	
	@Test
	public void testHoldWhenOnlyMinIsMentionedOnly() {
		SeatHold seatsHeld = tsi.findAndHoldSeats(18,  Optional.of(3), null, "Test@email.com");
		assertEquals("No Of Seats Held",18,seatsHeld.getHeldSeats().size());
		for(Seat seat:seatsHeld.getHeldSeats()) {
			assertEquals("All Seats are not in same level",3,seat.getLevel().getLevelId());
		}
	}
	
	@Test
	public void testHoldOnlyWhenMaxIsMentionedOnly() {
		//comment out each level to see how seats are booked on first available level
		mockReserveSeats(l1,8);
		mockReserveSeats(l2,5);
		SeatHold seatsHeld = tsi.findAndHoldSeats(18,null,Optional.of(4), "Test@email.com");
		assertEquals("No Of Seats Held",18,seatsHeld.getHeldSeats().size());
		for(Seat seat:seatsHeld.getHeldSeats()) {
			//checking level 3 here since not enough seats are available in other levels
			assertEquals("All Seats are not in same level",3,seat.getLevel().getLevelId());
		}
	}
	
	@Test 
	public void testHoldWhenNotEnoughSeatsInLevels() {
		thrown.expect(SeatsNotFoundException.class);
        thrown.expectMessage(startsWith("Could not hold"));
		mockReserveSeats(l1,8);
		mockReserveSeats(l2,5);
		mockReserveSeats(l3,9);
		mockReserveSeats(l4,17);
		SeatHold seatsHeld = tsi.findAndHoldSeats(18,Optional.of(1),Optional.of(4), "Test@email.com");
		assertEquals("No Of Seats Held",18,seatsHeld.getHeldSeats().size());
		for(Seat seat:seatsHeld.getHeldSeats()) {
			assertEquals("All Seats are not in same level",3,seat.getLevel().getLevelId());
		}
	}
	
	@Test
	public void testReserveHoldSeatsValid() {
		SeatHold seatsHeld = tsi.findAndHoldSeats(18,null,null, "Test@email.com");
		String status = tsi.reserveSeats(seatsHeld.getSeatHoldId(), seatsHeld.getCustomerEmail());
		assertTrue(status.equals(TicketServiceImpl.BOOKING_SUCCESS));
	}
	
	@Test
	public void testReserveHoldSeatsNullEmail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("Invalid Email Id"));
		SeatHold seatsHeld = tsi.findAndHoldSeats(18,null,null,null);
		tsi.reserveSeats(seatsHeld.getSeatHoldId(), seatsHeld.getCustomerEmail());
		
	}
	
	@Test
	public void testReserveHoldSeatsMistMatchEmailId() {
		SeatHold seatsHeld = tsi.findAndHoldSeats(18,null,null, "Test@email.com");
		String status = tsi.reserveSeats(seatsHeld.getSeatHoldId(),"Alternate@email.com");
		assertTrue(status.equals(TicketServiceImpl.BOOKING_FAILURE));
	}
	
	@Test
	public void testReserveHoldSeatsInvalidSeatId() {
		SeatHold seatsHeld = tsi.findAndHoldSeats(18,null,null, "Test@email.com");
		String status = tsi.reserveSeats(1245,seatsHeld.getCustomerEmail());
		assertTrue(status.equals(TicketServiceImpl.BOOKING_FAILURE));
	}
	
	@Test
	public void testReserveHoldSeatsNullEmailId() {
		SeatHold seatsHeld = tsi.findAndHoldSeats(18,null,null,"Test@email.com");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("Invalid Email Id"));
		tsi.reserveSeats(seatsHeld.getSeatHoldId(),null);
	}
	
	@Test
	public void testReserveHoldSeatsDuplicateCall() {
		SeatHold seatsHeld = tsi.findAndHoldSeats(18,null,null,"Test@email.com");
		tsi.reserveSeats(seatsHeld.getSeatHoldId(),seatsHeld.getCustomerEmail());
		String duplicateCall = tsi.reserveSeats(seatsHeld.getSeatHoldId(),seatsHeld.getCustomerEmail());
		assertTrue(duplicateCall.equals(TicketServiceImpl.BOOKING_DUPLICATE));
	}
	
	private void mockReserveSeats(Level l,int noOfSeatsLeft) {
		for(int i=0;i<l.getSeats().size()-noOfSeatsLeft;i++) {
			l.getSeats().get(i).setStatus(Status.RESERVED);
		}		
	}
	
	private long getAvilableSeatCountInLevel(int levelId) {
		List<Level> getSpecificLevel = vc.getLevels().stream().filter(level -> level.getLevelId() == levelId).collect(Collectors.toList());
		if(getSpecificLevel.size() != 1) {
			throw new IllegalArgumentException("List should not be  larger than 1");
		}
		return getSpecificLevel.get(0).getSeats().stream().filter(s-> s.getStatus() == Status.AVAILABLE).count();
	}

}
