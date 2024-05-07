package sys.airline.airline_apis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sys.airline.airline_apis.models.Aircraft;
import sys.airline.airline_apis.services.AircraftService;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = "/aircrafts")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @RequestMapping(method = RequestMethod.POST)
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {

        aircraftService.createAircraft(aircraft);
        return aircraft;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Aircraft> getAircrafts() {

        return aircraftService.getAircrafts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Aircraft getAircraft(@PathVariable Long id) {

        return aircraftService.getAircraft(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteAircraft(@PathVariable Long id) {

        aircraftService.deleteAircraft(id);
        return "Aircraft with ID " + id + " has been deleted.";
    }
}
