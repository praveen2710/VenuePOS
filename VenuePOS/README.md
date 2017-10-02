# Ticket Service(VenuePOS)
This app will facilitate the process of booking seats at a venue.

## Services Implemented
### http://localhost:8090/api/v1/emptySeat/{level_id}
To retrieve no of seats currently available in venue .  This can be used to search for seats at a specific level 
* **Request Type :** Get
* **Parameters :** 

		level_id (Optional)

	This will get you available seats in a specific level if it is valid

* **Return**

		An integer indication no of empty seats

* **Exceptions**

	* When a level not present in venue is passed
	
### http://localhost:8090/api/v1/seats/hold

This will hold the best seats in a level for no of seats requested by customer.
The customer can optionally range of levels where he wants booking.If none provided it will default from closest to farthest away.
If there are not enough seats in a level to fulfill requirements and given option the system will look for sets in next level.

* **Request Type :** POST
* **Parameters :** 

		numOfSeats(required)
		
		minLevel(Optional): Will look for seats from specified level
		
		maxLevel(Optional): Will look for seats till specified level
		
		customerEmail(required)

```
{
	"numOfSeats":"1",
	"minLevel":"1",
	"maxLevel":"4",
	"customerEmail":"Postman@email.com"
}
```

* **Return**

	A Json with Id and details of held seats held

```
{
    "bookingId": 30784,
    "heldSeats": [
        {
            "seatRow": 1,
            "seatNumber": 1,
            "priceOfSeat": 100,
            "level": "Orchestra"
        }
    ]
}
```

* **Exceptions**
	* When no of seats requested are <=0
	* When min or max level is not present in venue
	* When an email Id is not passed in
	* When requested seats are greater than any available in (Optionally in the given range if set)

### http://localhost:8090/api/v1/seats/reserve
A customer needs to provide the bookingId and also the email address to confirm the reservation of previous booked seats.

* **Request Type :** POST
* **Parameters :** 

		seatHoldId(required): The Id provided when booking seats
		
		customerEmail(required):
```
{
	"bookingId":"351085",
	"customerEmail":"Postman@email.com"
}
```

* **Return**

		String Indicating one of the three things
		
		* Booking Confirmed: The bookingId and email were a match and the booking is now confirmed
		
		* Booking could not be found to confirm : This indicates that either Id and email were not a match or your reservation has expired
		
		* Booking has been reserved already: This indicates that a confirmation request was made earlier and has been processed.

* **Exceptions**

	* When the booking Id is <=0
	* When an email Id is not passed in

## Implementation Details Required 
* ~~Using Java 8 concepts like Optional,Stream,Instant to satisfy the needs~~
* The seat score mechanism is fairly simple as of now and cannot handle the venue adding/removing custom seats. 
* ~~Need to implement a good way to score seats in a level~~
* ~~logic to select best available seats for a customer~~
* ~~logic to handle switch levels when seat no avalaible in a level~~
* ~~defensive coding on getter of list to prevent them from setting from getters~~
* ~~some way to save seat hold and then confirm them once done~~
* ~~way to implement logic to clearing expired seat holds~~
* ~~Java docs~~

## Testing Considerations
* ~~When there are no seats available in venue~~
* ~~When no seats are available in a level~~
* ~~When the min level that customer has requested does not have seat but other level has~~
* ~~When the any of the levels customer has requested does not have seats~~
* ~~When the customer does not give the min but gives the max and vice versa~~
* ~~When the seatHoldId matches for confirmation but the email does not~~
* ~~When the seatHold has expired for the seatHoldIds~~

## Implementation Details Good to have
* Dynamic seat scoring mechanism to accomodate adding seats on the fly
* synchronization 
* API to create venue/add level and seats to a level on the fly
* Validate email address
* Add entities to save data to a database
* ~~logging at different levels~~
* ~~Need to implement custom exception for various scenario's.~~
* Use autowired
* Add logic to save the seats by different events at venue

## Miscellaneous
* ~~reword the test cases wordings~~ 
* add code to throw exception when trying to add duplicates
* ~~update id on venue to something more closely related~~