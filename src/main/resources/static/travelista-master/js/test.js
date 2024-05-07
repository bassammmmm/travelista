function populateAirportsSelect() {
    $.ajax({
        url: 'http://localhost:8000/airports',
        type: 'GET',
        dataType: 'json',
        headers: {
            Authorization: `Bearer ${sessionStorage.getItem('token')}` // Add Authorization header with token
          },
        success: function (data) {
            var departureSelect = $('#departureAirport select');
            var destinationSelect = $('#destinationAirport select');
            departureSelect.empty(); // Clear existing options
            destinationSelect.empty(); // Clear existing options

            // Add default option to both select elements
            departureSelect.append('<option selected disabled>From Airport</option>');
            destinationSelect.append('<option selected disabled>To Airport</option>');

            $.each(data, function (index, airport) {
                departureSelect.append('<option value="' + airport.name + '">' + airport.name + '</option>');
                destinationSelect.append('<option value="' + airport.name + '">' + airport.name + '</option>');
            });

            // Event handler for when a departure airport is selected
            departureSelect.change(function () {
                var selectedDepartureAirportId = $(this).val();
                // Disable the selected departure airport in the destination select
                destinationSelect.find('option').prop('disabled', false);
                destinationSelect.find('option[value="' + selectedDepartureAirportId + '"]').prop('disabled', true);
            });
        },
        error: function () {
            console.log("Error fetching airports");
        }
    });
}

$(document).ready(function () {

    populateAirportsSelect();

    // Handle form submission
    $('#searchFlightsForm').submit(function (event) {
        event.preventDefault();
        var departureAirport = $('#departureAirport select').val();
        var destinationAirport = $('#destinationAirport select').val();
        var departureDate = $('#departureDate').val();

        // Redirect to flights.html with query parameters
        window.location.href = `flights.html?departureAirport=${departureAirport}&destinationAirport=${destinationAirport}&departureDate=${departureDate}`;
    });

    var urlParams = new URLSearchParams(window.location.search);
    var departureAirport = urlParams.get('departureAirport');
    var destinationAirport = urlParams.get('destinationAirport');
    var departureDate = urlParams.get('departureDate');
    console.log('departureAirport:', departureAirport);
    console.log('destinationAirport:', destinationAirport);
    console.log('departureDate:', departureDate);
    
    $.ajax({
        url: 'http://localhost:8000/flights',
        type: 'GET',
        dataType: 'json',
        headers: {
            Authorization: `Bearer ${sessionStorage.getItem('token')}` // Add Authorization header with token
          },
        data: {
            departureAirport: departureAirport,
            destinationAirport: destinationAirport,
            departureDate: departureDate
        },
        success: function (data) {
            var filteredFlights = data.filter(function (flight) {
                return flight.route.departureAirport.name === departureAirport &&
                       flight.route.destinationAirport.name === destinationAirport ||
                       flight.departureDate === departureDate;
            });
    
            if (filteredFlights.length > 0) {
                filteredFlights.forEach(function (flight) {
                    var flightCard = `
                        <div class="col-lg-4">
                            <div class="single-destinations">
                                <div class="details">
                                    <ul class="package-list">
                                        <li class="d-flex justify-content-between align-items-center">
                                            <span>Date</span>
                                            <span>${flight.departureDate}</span>
                                        </li>
                                        <li class="d-flex justify-content-between align-items-center">
                                            <span>Departure Time</span>
                                            <span>${flight.departureTime}</span>
                                        </li>
                                        <li class="d-flex justify-content-between align-items-center">
                                            <span>Arrival Time</span>
                                            <span>${flight.arrivalTime}</span>
                                        </li>
                                        <li class="d-flex justify-content-between align-items-center">
                                            <span>From Airport</span>
                                            <span>${flight.departureAirportName}</span>
                                        </li>
                                        <li class="d-flex justify-content-between align-items-center">
                                            <span>To Airport</span>
                                            <span>${flight.destinationAirportName}</span>
                                        </li>
                                        <li class="d-flex justify-content-between align-items-center">
                                            <span>Price per person</span>
                                            <a href="booking.html?flightId=${flight.id}"  class="price-btn">$${flight.price}</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    `;
                    $('#flightCards').append(flightCard);
                });
            } else {
                $('#flightCards').html('<p>No flights found.</p>');
            }
        },
        error: function () {
            console.log("Error fetching flights");
        }
    });
    
});

$(document).ready(function () {
    // Get flight ID from URL parameter
    var urlParams = new URLSearchParams(window.location.search);
    var flightId = urlParams.get('flightId');

    // Use the flight ID to fetch the flight details from the backend
    $.ajax({
        url: 'http://localhost:8000/flights/' + flightId,
        type: 'GET',
        dataType: 'json',
        headers: {
            Authorization: `Bearer ${sessionStorage.getItem('token')}` // Add Authorization header with token
          },
        success: function (flight) {
            // Populate the booking form with the flight details
            $('#departureAirport').text(flight.route.departureAirportName);
            $('#destinationAirport').text(flight.route.destinationAirportName);
            $('#departureTime').text(flight.departureTime);
            $('#arrivalTime').text(flight.arrivalTime);
           // $('#price').text('$' + flight.price.toFixed(2));
        },
        error: function () {
            console.log("Error fetching flight details");
        }
    });

    // Handle form submission
    /* $('#addBooking').submit(function (event) {
        event.preventDefault();

        // Create a new booking object
        var bookingData = {
            // Include any additional booking data here
        };

        // Send the booking request to the backend
        $.ajax({
            url: 'http://localhost:8000/bookings',
            type: 'POST',
            contentType: 'application/json',
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem('token')}` // Add Authorization header with token
              },
            data: JSON.stringify(bookingData),
            success: function (response) {
                console.log(response);
                // Redirect to the ticket page
                window.location.href = 'ticket.html';
            },
            error: function () {
                console.log("Error booking flight");
            }
        });
    }); */
});

$('#addBooking').submit(function (event) {
    event.preventDefault();

    // Get the customer_id from the user data in the session
    var customer_id = sessionStorage.getItem('coustmer_id');

    // Get the flight_id from the URL
    var urlParams = new URLSearchParams(window.location.search);
    var flight_id = urlParams.get('flightId');

    // Get the number of tickets and starting seat number
    var numOfTickets = parseInt($('#numOfTickets').val());
    var startSeatNumber = parseInt($('#number').val());

    // Create an array to hold the tickets
    var tickets = [];

    // Loop through the number of tickets and create a ticket object for each seat
    for (var i = 0; i < numOfTickets; i++) {
        var ticket = {
            "seat": {
                "number": startSeatNumber + i,
                "type": $('#type').val() // Assuming the select element has the id 'type'
            },
            "price": 220 // Assuming the price is fixed for all tickets
        };
        tickets.push(ticket);
    }

    var bookingData = {
        "customer_id": customer_id,
        "flight_id": flight_id,
        "tickets": tickets
    };

    $.ajax({
        url: 'http://localhost:8000/bookings',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            Authorization: `Bearer ${sessionStorage.getItem('token')}`
        },
        data: JSON.stringify(bookingData),
        success: function (response) {
            console.log(response);
            // Redirect to the ticket page
            window.location.href = 'ticket.html';
        },
        error: function () {
            console.log("Error booking flight");
        }
    });

    // Clear the form fields
    $('#numOfTickets').val('');
    $('#number').val('');
    $('#type').val('');
});

// Get the user id from the session storage
var user_id = sessionStorage.getItem('coustmer_id');

// Get the user id from the session storage

$.ajax({
    url: `http://localhost:8000/bookings/user/${user_id}`,
    type: 'GET',
    contentType: 'application/json',
    headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
    },
    success: function (response) {
        console.log(response);

        // Iterate over each booking and create a card
        response.forEach(function(booking) {
            var firstName = booking.customer.firstName;
            var departureDate = booking.flight.departureDate;
            var departureTime = booking.flight.departureTime;
            var arrivalTime = booking.flight.arrivalTime;
            var departureAirport = booking.flight.route.departureAirport.name;
            var destinationAirport = booking.flight.route.destinationAirport.name;
            var price = booking.tickets[0].price;
            var seatNumber = booking.tickets[0].seat.number;
            var seatClass = booking.tickets[0].seat.type;
            var numberOfTickets = booking.tickets.length;


            // Create a new card for each booking
            var cardHtml = `
                <div class="col-lg-4">
                    <div class="single-destinations">
                        <div class="details">
                            <ul class="package-list">
                                <li class="d-flex justify-content-between align-items-center">
                                    <span>First Name</span>
                                    <span>${firstName}</span>
                                </li>
                                <li class="d-flex justify-content-between align-items-center">
                                    <span>Departure Date</span>
                                    <span>${departureDate}</span>
                                </li>
                                <li class="d-flex justify-content-between align-items-center">
                                    <span>Departure Time</span>
                                    <span>${departureTime}</span>
                                </li>
                                <li class="d-flex justify-content-between align-items-center">
                                    <span>Arrival Time</span>
                                    <span>${arrivalTime}</span>
                                </li>
                                <li class="d-flex justify-content-between align-items-center">
                                    <span>Departure Airport</span>
                                    <span>${departureAirport}</span>
                                </li>
                                <li class="d-flex justify-content-between align-items-center">
                                    <span>Destination Airport</span>
                                    <span>${destinationAirport}</span>
                                </li>
                                <li class="d-flex justify-content-between align-items-center">
                                    <span>Price per person</span>
                                    <span>$${price}</span>
                                </li>
                                <li class="d-flex justify-content-between align-items-center">
                                    <span>Seat Number</span>
                                    <span>${seatNumber}</span>
                                </li>
                                <li class="d-flex justify-content-between align-items-center">
                                    <span>Seat Class</span>
                                    <span>${seatClass}</span>
                                </li>
                                <li class="d-flex justify-content-between align-items-center">
                                    <span>Number of Tickets</span>
                                    <span>${numberOfTickets}</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            `;

            // Append the card to the container
            $('#bookingContainer').append(cardHtml);
        });
    },
    error: function () {
        console.log("Error fetching booking details");
    }
});

				// Check if there is a token in session storage
if (sessionStorage.getItem('token')) {
    // Show the logout button
    document.getElementById('logout-btn').style.display = 'block';
  
    // Add event listener to logout button
    document.getElementById('logout-btn').addEventListener('click', function() {
		console.log("Logout button clicked");
      sessionStorage.removeItem('token');
      sessionStorage.removeItem('coustmer_id');

  
      // Hide the logout button
      document.getElementById('logout-btn').style.display = 'none';
  
      window.location.href = "sneat-1.0.0/html/auth-register-basic.html";
      // Add your logout logic here
    });
  }
  
			