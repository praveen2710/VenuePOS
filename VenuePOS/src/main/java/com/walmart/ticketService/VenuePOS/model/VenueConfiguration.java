package com.walmart.ticketService.VenuePOS.model;

import java.util.HashSet;

/**
 * This defines the details for the venue
 * @author PB033954
 *
 */
public class VenueConfiguration {
	private String name;
	private HashSet<Level> levels = new HashSet<>();
	//TODO add logic for for time limit for hold
	private static int VENUERESERVATIONHOLDTIME=900;
	
	public VenueConfiguration(String name, HashSet<Level> levels) {
		super();
		this.name = name;
		this.levels = levels;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashSet<Level> getLevels() {
		return levels;
	}
	public void setLevels(HashSet<Level> levels) {
		this.levels = levels;
	}
	
	@Override
	public String toString() {
		return "VenueConfiguration [name=" + name + ", levels=" + levels + "]";
	}
	
	public static int getVENUERESERVATIONHOLDTIME() {
		return VENUERESERVATIONHOLDTIME;
	}
	
	public static void setVENUERESERVATIONHOLDTIME(int vENUERESERVATIONHOLDTIME) {
		if(vENUERESERVATIONHOLDTIME>0) {
		   VENUERESERVATIONHOLDTIME = vENUERESERVATIONHOLDTIME;
		}
	}
	
	
}
