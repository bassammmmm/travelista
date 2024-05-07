package sys.airline.airline_apis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sys.airline.airline_apis.models.Booking;
import sys.airline.airline_apis.services.BookingService;
import sys.airline.airline_apis.utils.BookingHelper;

@RestController
@RequestMapping(value = "/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingHelper booking) {
        Booking savedBooking = bookingService.createBooking(booking.getCustomer_id(), booking.getFlight_id(), booking.getTickets());
        return ResponseEntity.ok(savedBooking);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<Booking> getAllBookings() {

        return bookingService.getBookings();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Booking getBooking(@PathVariable Long id) {

        return bookingService.getBooking(id);
    }

    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public List<Booking> getUserBookings(@PathVariable Long id) {

        return bookingService.getUserBookings(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteBooking(@PathVariable Long id) {

        bookingService.deleteBooking(id);
        return "Booking with ID " + id + " has been deleted.";
    }
}

