package sys.airline.airline_apis.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import sys.airline.airline_apis.utils.SeatClass;

@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Enumerated(EnumType.STRING)
    private SeatClass type;

    @ManyToOne
    private Flight flight;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public SeatClass getType() {
        return type;
    }

    public void setType(SeatClass type) {
        this.type = type;
    }


}