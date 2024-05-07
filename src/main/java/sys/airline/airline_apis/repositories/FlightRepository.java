package sys.airline.airline_apis.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import sys.airline.airline_apis.models.Flight;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {

    List<Flight> getFlightsByRouteId(Long Id);

}
