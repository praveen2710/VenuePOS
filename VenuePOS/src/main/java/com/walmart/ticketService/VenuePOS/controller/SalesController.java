package com.walmart.ticketService.VenuePOS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class SalesController {
	
	@RequestMapping(value = "emptySeats", method = RequestMethod.GET)
	public String listAvailableSeats() {
		return "All Available Seats in Venue";
	}
}
