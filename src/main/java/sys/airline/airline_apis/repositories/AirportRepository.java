package sys.airline.airline_apis.repositories;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import sys.airline.airline_apis.models.Airport;

@Repository
public interface AirportRepository extends CrudRepository<Airport, Long> {
    Airport findByName(Optional<String> depAirport);
    system.out.println()
}
