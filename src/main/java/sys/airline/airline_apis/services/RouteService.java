package sys.airline.airline_apis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import sys.airline.airline_apis.models.Route;
import sys.airline.airline_apis.repositories.AirportRepository;
import sys.airline.airline_apis.repositories.RouteRepository;

@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepository;
    AirportRepository airportRepository;

    public boolean createRoute(Route route){
        Route routeExists = routeRepository.findByDepartureAirportAndDestinationAirport(route.getDepartureAirport(), route.getDestinationAirport());
        
        if (routeExists != null) {
            return false;
        }
        else {
            routeRepository.save(route);
            return true;
        }
        

    }

    public Route getRoute(Long Id) {
        return routeRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException(
            "Route with ID " + Id + " is not found"
        ));
    }

    public void deleteRoute(Long Id) {
        routeRepository.deleteById(Id);
    }

    public List<Route> getRoutes() {
        
        return (List<Route>) routeRepository.findAll();
    }
    

}
