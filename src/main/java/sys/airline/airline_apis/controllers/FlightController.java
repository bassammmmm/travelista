package sys.airline.airline_apis.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sys.airline.airline_apis.models.Flight;
import sys.airline.airline_apis.services.FlightService;

@RestController
@RequestMapping(value = "/flights")
public class FlightController {
    
    @Autowired
    private FlightService flightService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createFlight(@Valid @RequestBody Flight flight) {
            
            flightService.createFlight(flight);
    
            return ResponseEntity.ok("Flight created successfully.");
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Flight> getAllFlights(
            @RequestParam("departureAirport") Optional<String> departureAirport,
            @RequestParam("destinationAirport") Optional<String> destinationAirport) {

        return flightService.getFlights(departureAirport, destinationAirport);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Flight getFlight(@PathVariable Long id) {

        return flightService.getFlight(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteFlight(@PathVariable Long id) {

        flightService.deleteFlight(id);
        return "Flight with ID " + id + " has been deleted.";
    }
}
