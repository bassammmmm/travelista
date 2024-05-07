package sys.airline.airline_apis.models;

import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;


@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private User customer;
    
    @ManyToOne
    private Flight flight;

    @OneToMany(mappedBy = "booking", orphanRemoval = true)
    private List<Ticket> tickets;

    private LocalDateTime bookingDate;

    public Booking(User customer, Flight flight) {
        this.customer = customer;
        this.flight = flight;
    }
    public Booking() {

    }

    @PrePersist
    public void prePersist() {
        bookingDate = LocalDateTime.now();
    }

    public Long getId() {
        return Id;
    }

    public User getCustomer() {
        return customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
}
