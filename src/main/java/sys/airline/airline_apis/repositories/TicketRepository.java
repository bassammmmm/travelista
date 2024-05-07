package sys.airline.airline_apis.repositories;

import org.springframework.data.repository.CrudRepository;

import sys.airline.airline_apis.models.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
