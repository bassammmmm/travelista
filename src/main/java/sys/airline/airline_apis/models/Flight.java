package sys.airline.airline_apis.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private Route route;

    @ManyToOne
    private Aircraft aircraft;

    private double price;

    private int numberOfSeats;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    private LocalDate departureDate;

    private LocalDate arrivalDate;

    private boolean isRoundTrip;

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Long getId() {
        return Id;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public void setRoundTrip(boolean isRoundTrip) {
        this.isRoundTrip = isRoundTrip;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureAirportName() {
        return route.getDepartureAirport().getName();
    }

    public String getDestinationAirportName() {
        return route.getDestinationAirport().getName();
    }

    public String getAircraftModel() {
        return aircraft.getModel();
    }
}
