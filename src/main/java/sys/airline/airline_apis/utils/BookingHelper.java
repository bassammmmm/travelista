package sys.airline.airline_apis.utils;

import sys.airline.airline_apis.models.Booking;
import sys.airline.airline_apis.models.Ticket;

import java.util.List;

public class BookingHelper {
    
    private Long customer_id;
    private Long flight_id;


    private List<Ticket> tickets;

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(Long flight_id) {
        this.flight_id = flight_id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

}
