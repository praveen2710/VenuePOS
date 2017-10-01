package com.walmart.ticketService.VenuePOS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.SystemPropertyUtils;

import com.walmart.ticketService.VenuePOS.model.Level;
import com.walmart.ticketService.VenuePOS.model.Seat;
import com.walmart.ticketService.VenuePOS.model.SeatHold;
import com.walmart.ticketService.VenuePOS.model.Status;
import com.walmart.ticketService.VenuePOS.model.VenueConfiguration;
import com.walmart.ticketService.VenuePOS.repository.BookingRepository;
import com.walmart.ticketService.VenuePOS.service.TicketServiceImpl;

@SpringBootApplication
public class VenuePosApplication {

	public static void main(String[] args) throws InterruptedException {		
		SpringApplication.run(VenuePosApplication.class, args);
	}
	
}
