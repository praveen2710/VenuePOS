package com.walmart.ticketService.VenuePOS.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.walmart.ticketService.VenuePOS.customexceptions.SeatsNotFoundException;
import com.walmart.ticketService.VenuePOS.model.Level;
import com.walmart.ticketService.VenuePOS.model.Seat;
import com.walmart.ticketService.VenuePOS.model.SeatHold;
import com.walmart.ticketService.VenuePOS.model.Status;
import com.walmart.ticketService.VenuePOS.model.VenueConfiguration;
import com.walmart.ticketService.VenuePOS.repository.BookingRepository;
import com.walmart.ticketService.VenuePOS.util.TicketServiceUtil;

public class TicketServiceImpl implements TicketService {
	
	protected VenueConfiguration venuConfig;
	protected BookingRepository br;
	static Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);
	public static final String BOOKING_SUCCESS = "Booking Confirmed";
	public static final String BOOKING_FAILURE = "Booking Not found to Confirm";
	public static final String BOOKING_DUPLICATE = "Reservation has already been confirmed for this booking";
	
	public TicketServiceImpl(VenueConfiguration venuConfig,BookingRepository br) {
		this.venuConfig = venuConfig;
		this.br = br;
	}

	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		Optional<Integer> actualVenueLevel = TicketServiceUtil.checkIfNull(venueLevel);
		if(actualVenueLevel.isPresent() && !TicketServiceUtil.checkVenueLevel(actualVenueLevel.get(), venuConfig)) {
			LOGGER.error("Invalid level entered:" + venueLevel.get());
			throw new IllegalArgumentException("The level specified is not valid");
		}
		br.clearExpiredHoldSeats();
		return actualVenueLevel.map(level->findAllAvailableLevelCount(level)).orElseGet(()->findAllAvailable());
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		Optional<Integer> actualMinLevel = TicketServiceUtil.checkIfNull(minLevel);
		Optional<Integer> actualMaxLevel = TicketServiceUtil.checkIfNull(maxLevel);
		
		if(numSeats <= 0) {
			LOGGER.error("Invalid no of seats requested");	
			throw new IllegalArgumentException("Invalid no of seats requested");
		}
		
		if(customerEmail == null || customerEmail.isEmpty()) {
			LOGGER.error("No Customer Email Id was passed In");	
			throw new IllegalArgumentException("Invalid Email Id");
		}
		
		if(actualMinLevel.isPresent() && !TicketServiceUtil.checkVenueLevel(actualMinLevel.get(), venuConfig)) {
			LOGGER.error("Entered Min Level:"+actualMinLevel.get());	
			throw new IllegalArgumentException("Min level is not in range");
		}
		
		if(actualMaxLevel.isPresent() && !TicketServiceUtil.checkVenueLevel(actualMaxLevel.get(), venuConfig)) {
			LOGGER.error("Entered Max Level:"+actualMaxLevel.get());
			throw new IllegalArgumentException("Max level is not in range");
		}
		
		if(actualMinLevel.isPresent() && actualMaxLevel.isPresent()) {
			int min = actualMinLevel.get();
			int max = actualMaxLevel.get();
			if(min > max) {
				LOGGER.error("Entered Min Level:"+min+"> Max Level:"+max);
				throw new IllegalArgumentException("Min Level cannot be greater than max level");
			}
		}
		br.clearExpiredHoldSeats();
		Optional<SeatHold> sh =  doHoldSeats(numSeats,actualMinLevel,actualMaxLevel,customerEmail);
		br.addToHolding(sh);
		return sh.orElseThrow(() ->
        new SeatsNotFoundException("Could not hold requested amount of seats"));
	}

	private Optional<SeatHold> doHoldSeats(int numSeats, Optional<Integer> actualMinLevel, Optional<Integer> actualMaxLevel,
			String customerEmail) {
		int minLevel = actualMinLevel.orElseGet(()->TicketServiceUtil.getMinVenueLevel(venuConfig));
		int maxLevel  = actualMaxLevel.orElseGet(()->TicketServiceUtil.getMaxVenueLevel(venuConfig));
		Optional<SeatHold> sh = Optional.empty();
		Set<Seat> bestSeatsInLevel = null; 

		//This will loop and find the first possible level to book all requested Seats
		while(minLevel <= maxLevel){
			bestSeatsInLevel = getBestSeatsInLevel(numSeats,minLevel);
			if(bestSeatsInLevel.size() == numSeats) {
				break;
			}
			minLevel++;
		}
		
		if(bestSeatsInLevel.size() == numSeats) {
			sh = Optional.of(new SeatHold(customerEmail,bestSeatsInLevel));
		}
		return sh;
	}
	
	private Set<Seat> getBestSeatsInLevel(int numOfSeats,int venueLevel){
		Stream<Seat> seatStream;
		Set<Seat> selectedSeat = new HashSet<>();
		for(Level l:venuConfig.getLevels()) {
			if(l.getLevelId() == venueLevel) {
				seatStream = l.getSeats().stream();
				seatStream.filter(s-> s.getStatus() == Status.AVAILABLE)
							.sorted((Seat s1,Seat s2)->Float.compare(s1.getSeatScore(), s2.getSeatScore()))
							.limit(numOfSeats)
							.forEach(selectedSeat::add);
			}
		}
		return selectedSeat;
		
	}

	@Override
	public String reserveSeats(int bookingId, String customerEmail) {
		
		if(customerEmail == null || customerEmail.isEmpty()) {
			LOGGER.error("No Customer Email Id was passed In");	
			throw new IllegalArgumentException("Invalid Email Id");
		}
		
		if(bookingId <=0) {
			LOGGER.error("bookingId passed in is invalid");	
			throw new IllegalArgumentException("Invalid booking Id");
		}
		
		
		br.clearExpiredHoldSeats();
		Optional<SeatHold> retrieveForReservation = br.retrieveForConfirmation(bookingId,customerEmail);
		if(retrieveForReservation.isPresent() && br.confirmReservation(retrieveForReservation.get())) {
			return BOOKING_SUCCESS;
		}else if(br.retrieveReservedSeats(bookingId, customerEmail)) {
			return BOOKING_DUPLICATE;
		}
		return BOOKING_FAILURE;
	}
	
	/**
	 * This will return count of seats in a particular level
	 * @param n level number
	 * @return
	 */
	private int findAllAvailableLevelCount(int n) {
		int seatCount = 0;
		for(Level l: venuConfig.getLevels()) {
			if(l.getLevelId() == n) {
				for(Seat s: l.getSeats()) {
					if(s.getStatus().equals(Status.AVAILABLE)){
						seatCount++;
					}
				}
			}
		}
		return seatCount;
	}
	
	/**
	 * This will return count of all empty seats in venue
	 * @return
	 */
	private int findAllAvailable() {
		int seatCount = 0;
		for(Level l: venuConfig.getLevels()) {
			for(Seat s: l.getSeats()) {
				if(s.getStatus().equals(Status.AVAILABLE)){
					seatCount++;
				}
			}
		}
		return seatCount;
	}
}
