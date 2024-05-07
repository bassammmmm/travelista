package sys.airline.airline_apis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sys.airline.airline_apis.models.Seat;
import sys.airline.airline_apis.services.SeatService;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping(value = "/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @RequestMapping(method = RequestMethod.POST)
    public Seat createSeat(@Valid @RequestBody Seat seat) {

        seatService.createSeat(seat);
        return seat;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Seat> getSeats() {

        return seatService.getSeats();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Seat getSeat(@PathVariable Long id) {

        return seatService.getSeat(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteSeat(@PathVariable Long id) {

        seatService.deleteSeat(id);
        return "Seat with ID " + id + " has been deleted.";
    }
}

