# Ticket Service(VenuePOS)
This app will facilitate the process of booking seats at a venue.

## Services Implemented
* URL HERE
To retrieve no of seats currently available in venue . Optionally this can be used to search for seats at a specific level 
* URL HERE
This will hold the best seats in a level for no of seats requested by customer.
The customer can optionally range of levels where he wants booking.If none provided it will default from closest to farthest away.
If there are not enough seats in a level to fulfill requirements and given option the system will look for sets in next level.
* URL HERE
A customer can provide the holdId and also the email address to confirm the reservation of seats held by using above URL

### Implementation Details Required 
* Using Java 8 concepts like Optional,Stream,Instant to satisfy the needs
* The seat score mechanism is fairly simple as of now and cannot handle the venue adding/removing custom seats. 
* Need to implement a good way to score seats in a level
* logic to select best available seats for a customer
* logic to handle switch levels when seat no avalaible in a level
* defensive coding on getter of list to prevent them from setting from 
* some way to save seat hold and then confirm them once done
* way to implement logic to clearing expired seat holds.
* Java docs

### Testing Considerations
* When there are no seats available in venue
* When no seats are available in a level
* When the min level that customer has requested does not have seat but other level has
* When the any of the levels customer has requested does not have seats
* When the customer does not give the min but gives the max and vice versa
* When the seatHoldId matches for confirmation but the email does not
* When the seatHold has expired for the seatHoldIds
*

### Implementation Details Good to have
* Dynamic seat scoring mechanism to accomodate adding seats on the fly
* synchronization 
* API to create venue/add level and seats to a level on the fly
* Validate email address
* Add entities to save data to a database
* logging at different levels
* Need to implement custom exception for various scenario's.
* Use autowired
* Add logic to save the seats by events at venue

### Misc
* reword the test cases wordings 
* add code to throw exception when trying to add duplicates
* update id on venue to something more closely related