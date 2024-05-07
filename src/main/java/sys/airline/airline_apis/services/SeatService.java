package sys.airline.airline_apis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import sys.airline.airline_apis.models.Seat;
import sys.airline.airline_apis.repositories.SeatRepository;

@Service
public class SeatService {

    @Autowired
    SeatRepository seatRepository;


    public void createSeat(Seat Seat){
        seatRepository.save(Seat);
    }

    public Seat getSeat(Long Id) {
        return seatRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException(
            "Seat with ID " + Id + " is not found"
        ));
    }

    public void deleteSeat(Long Id) {
        seatRepository.deleteById(Id);
    }

    public List<Seat> getSeats() {
        
        return (List<Seat>) seatRepository.findAll();
    }
    

}
