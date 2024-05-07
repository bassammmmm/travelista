package sys.airline.airline_apis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sys.airline.airline_apis.models.Airport;
import sys.airline.airline_apis.services.AirportService;

@RestController

@RequestMapping(value = "/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public Airport createAirport(@Valid @RequestBody Airport airport) {

        airportService.createAirport(airport);
        return airport;
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Airport> getAirports() {

        return airportService.getAirports();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Airport getAirport(@PathVariable Long id) {

        return airportService.getAirport(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteAirport(@PathVariable Long id) {

        airportService.deleteAirport(id);
        return "Airport with ID " + id + " has been deleted.";
    }
}
