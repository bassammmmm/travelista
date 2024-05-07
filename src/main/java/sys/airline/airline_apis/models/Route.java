package sys.airline.airline_apis.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Route {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private Airport departureAirport;
    
    @ManyToOne
    private Airport destinationAirport; 

    private int distanceKilometers;

    private int durationMins;

    public Route() {

    }

    public Long getId() {
        return Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public Airport getDepartureAirport() {
        return departureAirport;
    }
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }
    public Airport getDestinationAirport() {
        return destinationAirport;
    }
    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }
    public int getDistanceKilometers() {
        return distanceKilometers;
    }
    public void setDistanceKilometers(int distanceKilometers) {
        this.distanceKilometers = distanceKilometers;
    }
    public int getDurationMins() {
        return durationMins;
    }
    public void setDurationMins(int durationMins) {
        this.durationMins = durationMins;
    }
    public String getDepartureAirportName() {
        return departureAirport.getName();
    }

    public String getDestinationAirportName() {
        return destinationAirport.getName();
    }
    public Route(Long departureAirportId, Long destinationAirportId, int distanceKilometers, int durationMins) {
        this.departureAirport = new Airport();
        this.departureAirport.setId(departureAirportId);
        this.destinationAirport = new Airport();
        this.destinationAirport.setId(destinationAirportId);
        this.distanceKilometers = distanceKilometers;
        this.durationMins = durationMins;
    }
}
