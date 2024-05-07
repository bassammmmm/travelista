package sys.airline.airline_apis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import sys.airline.airline_apis.models.Airport;
import sys.airline.airline_apis.models.Flight;
import sys.airline.airline_apis.models.Route;
import sys.airline.airline_apis.repositories.AircraftRepository;
import sys.airline.airline_apis.repositories.AirportRepository;
import sys.airline.airline_apis.repositories.FlightRepository;
import sys.airline.airline_apis.repositories.RouteRepository;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AircraftRepository aircraftRepository;

    public void createFlight(Flight flight){
            flightRepository.save(flight);

    }

    public Flight getFlight(Long Id) {
        return flightRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException(
            "Flight with ID " + Id + " is not found"
        ));
    }

    public void deleteFlight(Long Id) {
        flightRepository.deleteById(Id);
    }


    public List<Flight> getFlights(Optional<String> depAirport, Optional<String> destAirport) {
        
        if (!depAirport.isPresent() || !destAirport.isPresent()){
            return (List<Flight>) flightRepository.findAll();
        }
        
        Airport departureAirport = airportRepository.findByName(depAirport);
        Airport destinationAirport = airportRepository.findByName(destAirport);

        Route flight_route = routeRepository.findByDepartureAirportAndDestinationAirport(departureAirport, destinationAirport);

        List<Flight> flights = flightRepository.getFlightsByRouteId(flight_route.getId());

        System.out.println(flights);
        return (List<Flight>) flights;

        
    }
    

}
