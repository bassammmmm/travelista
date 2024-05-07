package sys.airline.airline_apis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import sys.airline.airline_apis.models.Airport;
import sys.airline.airline_apis.repositories.AirportRepository;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;


    public void createAirport(Airport airport){
        airportRepository.save(airport);
    }

    public Airport getAirport(Long Id) {
        return airportRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException(
            "Airport with ID " + Id + " is not found"
        ));
    }

    public void deleteAirport(Long Id) {
        airportRepository.deleteById(Id);
    }

    public List<Airport> getAirports() {
        
        return (List<Airport>) airportRepository.findAll();
    }
    

}
