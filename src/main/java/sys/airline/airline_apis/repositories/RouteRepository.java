package sys.airline.airline_apis.repositories;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import sys.airline.airline_apis.models.Airport;
import sys.airline.airline_apis.models.Route;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {

    Route findByDepartureAirportAndDestinationAirport(Airport departureAirport, Airport destinationAirport);
}
