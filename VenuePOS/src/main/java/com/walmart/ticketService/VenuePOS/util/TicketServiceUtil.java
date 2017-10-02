package com.walmart.ticketService.VenuePOS.util;

import java.util.Optional;

import com.walmart.ticketService.VenuePOS.model.VenueConfiguration;

public class TicketServiceUtil {
	
	/**
	 * This will check if null was passed in as a parameter
	 * @param optional
	 * @return Optional if present otherwise {@link Optional#empty()}
	 */
	public static <T> Optional<T> checkIfNull(Optional<T> optional) {
		if (optional==null)
			return Optional.empty();
		else
			return optional;
	}
	
	/**
	 * Return the least level that is present in venue 
	 * @param venuConfig configuration
	 * @return the least level in the venue
	 */
	public static int getMinVenueLevel(VenueConfiguration venuConfig) {
		Optional<Integer> minLevel = venuConfig.getLevels().stream().map(s-> s.getLevelId()).min(Integer::compare);
		return minLevel.orElse(-1);

	}
	
	/**
	 * This will validate if venue level exists
	 * @param level to validate
	 * @param venue configuration
	 * @return true if venue level exists false otherwise
	 */
	public static boolean checkVenueLevel(int level,VenueConfiguration venue) {
		return  venue.getLevels().stream().anyMatch(t -> t.getLevelId() == level);
	}
	
	/**
	 * This will get the highest level present in venue
	 * @param venuConfig configuration
	 * @return the highest level in venue
	 */
	public static int getMaxVenueLevel(VenueConfiguration venuConfig) {
		Optional<Integer> maxLevel = venuConfig.getLevels().stream().map(s-> s.getLevelId()).max(Integer::compare);
		return maxLevel.orElse(-1);
	}
}
