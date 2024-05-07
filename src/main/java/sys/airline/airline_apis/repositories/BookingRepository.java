package sys.airline.airline_apis.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import sys.airline.airline_apis.models.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findBookingsByCustomerId(Long Id);

}
