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
		
//		Level l1  = new Level(1,"Orchestra",25,50,new BigDecimal(100));
//		Level l2  = new Level(2,"Main",20,100,new BigDecimal(75));
//		Level l3  = new Level(3,"Balcony 1",15,100,new BigDecimal(50));
//		Level l4  = new Level(4,"Balcony 2",15,100,new BigDecimal(40));
//		
//		HashSet<Level> levels = new HashSet<>();
//		levels.add(l1);
//		levels.add(l2);
//		levels.add(l3);
//		levels.add(l4);
//		VenueConfiguration vc = new VenueConfiguration("Chicago Symphony Center",levels);
//		BookingRepository br = new BookingRepository();
//		TicketServiceImpl  tsl= new TicketServiceImpl(vc,br);
				
		SpringApplication.run(VenuePosApplication.class, args);
	}
	
}
