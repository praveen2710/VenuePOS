package com.walmart.ticketService.VenuePOS.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This defines the details for the venue
 * @author PB033954
 *
 */
public class VenueConfiguration {
	private String name;
	private Set<Level> levels;
	private static int VENUERESERVATIONHOLDTIME=900;
	
	public VenueConfiguration(String name, Set<Level> level) {
		super();
		this.name = name;
		this.levels = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Level> getLevels() {
		return Collections.unmodifiableSet(levels);
	}
	
	public void addLevel(Level level) {
		//defensive coding in case user has not passed levels initially
		if(this.levels==null) {
			this.levels = new HashSet<>();
		}
		this.levels.add(level);
	}
	
	public void addLevels(Set<Level> levels) {
		//defensive coding in case user has not passed levels initially
		if(this.levels==null) {
			this.levels = new HashSet<>();
		}
		this.levels.addAll(levels);
	}
	
	@Override
	public String toString() {
		return "VenueConfiguration [name=" + name + ", levels=" + levels + "]";
	}
	
	public static int getVENUERESERVATIONHOLDTIME() {
		return VENUERESERVATIONHOLDTIME;
	}
	
	public static void setVENUERESERVATIONHOLDTIME(int newHoldTime) {
		if(newHoldTime>0) {
		   VENUERESERVATIONHOLDTIME = newHoldTime;
		}
	}
	
	
}
