package sys.airline.airline_apis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import sys.airline.airline_apis.models.Aircraft;
import sys.airline.airline_apis.repositories.AircraftRepository;

@Service
public class AircraftService {

    @Autowired
    AircraftRepository aircraftRepository;


    public void createAircraft(Aircraft aircraft){
        aircraftRepository.save(aircraft);
    }

    public Aircraft getAircraft(Long Id) {
        return aircraftRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException(
            "Aircraft with ID " + Id + " is not found"
        ));
    }

    public void deleteAircraft(Long Id) {
        aircraftRepository.deleteById(Id);
    }

    public List<Aircraft> getAircrafts() {
        
        return (List<Aircraft>) aircraftRepository.findAll();
    }
    

}
