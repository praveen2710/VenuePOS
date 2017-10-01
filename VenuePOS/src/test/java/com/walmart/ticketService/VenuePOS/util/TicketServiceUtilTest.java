package com.walmart.ticketService.VenuePOS.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.walmart.ticketService.VenuePOS.model.Level;
import com.walmart.ticketService.VenuePOS.model.VenueConfiguration;

public class TicketServiceUtilTest {
	
	private VenueConfiguration vc;
	
	@Before
	public void setUp() {
		vc = new VenueConfiguration("TestVenue",null);
	}

	@Test
	public void testMinLevelWhenLevelsNotPresent() {
//		TicketServiceUtil.getMinVenueLevel(vc);
	}
	
	@Test 
	public void testGetMinLevelInVenue() {
		Level l4  = new Level(14,"Orchestra",25,50,new BigDecimal(100));
		Level l2  = new Level(16,"Main",20,100,new BigDecimal(75));
		Level l3  = new Level(15,"Balcony 1",15,100,new BigDecimal(50));
		Level l1  = new Level(37,"Balcony 2",15,100,new BigDecimal(40));
		
		Set<Level> levels = new HashSet<>();
		levels.add(l1);
		levels.add(l2);
		levels.add(l3);
		levels.add(l4);
		
		vc.addLevels(levels);
		int minLevel = TicketServiceUtil.getMinVenueLevel(vc);
		assertEquals("Min Level In Venue",14,minLevel);
	}

}
