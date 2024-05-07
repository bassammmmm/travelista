package sys.airline.airline_apis.repositories;

import org.springframework.data.repository.CrudRepository;

import sys.airline.airline_apis.models.Seat;

public interface SeatRepository extends CrudRepository<Seat, Long> {


}