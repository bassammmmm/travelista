package sys.airline.airline_apis.controllers;

import java.util.List;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sys.airline.airline_apis.models.Airport;
import sys.airline.airline_apis.models.Route;
import sys.airline.airline_apis.services.RouteService;


@RestController
@RequestMapping(value = "/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createRoute(@Valid @RequestBody Route route) {

        Boolean created = routeService.createRoute(route);
        if (!created) {
            return ResponseEntity.badRequest().body("Route already exists.");
        }
        return ResponseEntity.ok("Route created successfully.");
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Route> getRoutes() {

        return routeService.getRoutes();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Route getRoute(@PathVariable Long id) {

        return routeService.getRoute(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteRoute(@PathVariable Long id) {

        routeService.deleteRoute(id);
        return "Route with ID " + id + " has been deleted.";
    }
    @Data
    static class RouteCreationRequest {
        @Valid
        private Route route;

        @Valid
        private Airport departureAirport;

        @Valid
        private Airport destinationAirport;

    }
}

