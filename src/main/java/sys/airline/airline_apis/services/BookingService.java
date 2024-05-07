package sys.airline.airline_apis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import sys.airline.airline_apis.models.Booking;
import sys.airline.airline_apis.models.Flight;
import sys.airline.airline_apis.models.Ticket;
import sys.airline.airline_apis.models.User;
import sys.airline.airline_apis.repositories.BookingRepository;
import sys.airline.airline_apis.repositories.FlightRepository;
import sys.airline.airline_apis.repositories.TicketRepository;
import sys.airline.airline_apis.repositories.UserRepository;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FlightRepository flightRepository;

    @Transactional
    public Booking createBooking(Long customer_id, Long flight_id, List<Ticket> tickets) {
        
        int customerId = customer_id.intValue();
        User customer = userRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException(
            "User with ID " + customerId + " is not found"
        ));

        Flight flight = flightRepository.findById(flight_id).orElseThrow(() -> new EntityNotFoundException(
            "Flight with ID " + flight_id + " is not found"
        ));

        Booking booking = new Booking(customer, flight);

        Booking savedBooking = bookingRepository.save(booking);

        for (Ticket ticket : tickets) {
            ticket.setBooking(savedBooking);
            ticketRepository.save(ticket);
        }
        savedBooking.setTickets(tickets);
        return savedBooking;
    }

    public Booking getBooking(Long Id) {
        return bookingRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException(
            "Booking with ID " + Id + " is not found"
        ));
    }

    public List<Booking> getUserBookings(Long Id) {
        
        return (List<Booking>) bookingRepository.findBookingsByCustomerId(Id);
    }

    public void deleteBooking(Long Id) {
        bookingRepository.deleteById(Id);
    }

    public List<Booking> getBookings() {
        
        return (List<Booking>) bookingRepository.findAll();
    }
    

}
