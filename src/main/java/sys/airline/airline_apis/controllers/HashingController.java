package sys.airline.airline_apis.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sys.airline.airline_apis.config.CustomPasswordEncoder;

@RestController
public class HashingController {

    private final CustomPasswordEncoder customPasswordEncoder;

    public HashingController(CustomPasswordEncoder customPasswordEncoder) {
        this.customPasswordEncoder = customPasswordEncoder;
    }

    @PostMapping("/api/hash-password")
    public String hashPassword(@RequestBody String password) {
        return customPasswordEncoder.encode(password);
    }
}
