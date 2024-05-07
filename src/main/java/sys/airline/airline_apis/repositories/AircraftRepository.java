package sys.airline.airline_apis.repositories;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import sys.airline.airline_apis.models.Aircraft;

@Repository
public interface AircraftRepository extends CrudRepository<Aircraft, Long> {


}
