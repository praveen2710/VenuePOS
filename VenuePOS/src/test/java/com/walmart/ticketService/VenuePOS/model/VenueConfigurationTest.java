package com.walmart.ticketService.VenuePOS.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class VenueConfigurationTest {
	
	VenueConfiguration vc;
	
	@Before
	public void setUp() {
		Level l1 = new Level(1,"testBalcony",1,10,new BigDecimal(10));
		Set<Level> levels = new HashSet<>();
		levels.add(l1);
		vc = new VenueConfiguration("Test Venue", levels);
	}

	@Test
	public void testAddingOneLevelAfterCreation() {
		
		int intialSizeOfLevels = vc.getLevels().size();
		Level l2 = new Level(2,"testMain",1,10,new BigDecimal(10));
		vc.addLevel(l2);
		assertEquals("New Level should be added",++intialSizeOfLevels,vc.getLevels().size());
		
	}
	
	@Test
	public void testAddingManyLevelAfterCreation() {
		
		int intialSizeOfLevels = vc.getLevels().size();
		Set<Level> levelAddedLater = new HashSet<>(); 
		Level l2 = new Level(2,"testMain",1,10,new BigDecimal(10));
		Level l3 = new Level(3,"testMain2",1,10,new BigDecimal(10));
		levelAddedLater.add(l2);
		levelAddedLater.add(l3);
		vc.addLevels(levelAddedLater);
		assertEquals("New Levels are to be added",intialSizeOfLevels+2,vc.getLevels().size());
		
	}

	@Test
	public void testAddingManyLevelWithIntialNull() {
		vc = new VenueConfiguration("Test Venue", null); 
		Set<Level> levelAddedLater = new HashSet<>(); 
		Level l2 = new Level(2,"testMain",1,10,new BigDecimal(10));
		Level l3 = new Level(3,"testMain2",1,10,new BigDecimal(10));
		levelAddedLater.add(l2);
		levelAddedLater.add(l3);
		vc.addLevels(levelAddedLater);
		assertEquals("New Levels are to be added",2,vc.getLevels().size());
		
	}
	
	@Test
	public void testAddingOneLevelWithIntialNull() {
		vc = new VenueConfiguration("Test Venue", null); 
		Level l2 = new Level(2,"testMain",1,10,new BigDecimal(10));
		vc.addLevel(l2);
		assertEquals("New Level is to be added",1,vc.getLevels().size());
		
	}


}
