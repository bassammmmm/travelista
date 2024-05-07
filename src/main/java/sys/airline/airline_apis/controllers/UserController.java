package sys.airline.airline_apis.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sys.airline.airline_apis.config.CustomPasswordEncoder;
import sys.airline.airline_apis.models.AuthToken;
import sys.airline.airline_apis.models.User;
import sys.airline.airline_apis.repositories.TokenRepository;
import sys.airline.airline_apis.repositories.UserRepository;
import sys.airline.airline_apis.services.AuthServiceImp;
import sys.airline.airline_apis.services.JwtService;
import sys.airline.airline_apis.services.UserInfoService;
import sys.airline.airline_apis.utils.AuthRequest;
import sys.airline.airline_apis.utils.AuthenticationResponse;
import sys.airline.airline_apis.utils.RegisterRequest;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;
    @Autowired
    private AuthServiceImp authServiceImp;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;


    @RequestMapping(path = "/register", method=RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> addUser(@Valid @RequestBody RegisterRequest regRequest) {

        if (!customPasswordEncoder.encode(regRequest.getPassword()).equals(customPasswordEncoder.encode(regRequest.getPassword()))){
            Map<String, String> errors = new HashMap<>();
            errors.put("passwordConfirmation", "Password and password confirmation do not match.");
            return ResponseEntity.badRequest().body((AuthenticationResponse) errors);
        }
        if(userRepository.existsByEmail(regRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(authServiceImp.addUser(regRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> logIn(@RequestBody AuthRequest signInRequest) {
        
        AuthenticationResponse result = authServiceImp.logIn(signInRequest);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email or password is incorrect.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userInfoService.getAllUsers();
    }

    @GetMapping("/getUsers/{id}")
    public User getAllUsers(@PathVariable Integer id){
        return userInfoService.getUserById(id);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll(){
        return userInfoService.deleteAllUsers();
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        String token = jwtService.extractTokenFromRequest(request);
        String username = jwtService.extractUsername(token);

        // Delete token from the database
        jwtService.deleteTokenByUsername(username);

        // Clear token from session or any other cleanup
        jwtService.clearTokenFromSession(request);

        return ResponseEntity.ok(Collections.singletonMap("message", "Logged out successfully"));
    }

    @GetMapping("/getAllTokens")
    public List<AuthToken> getAllTokens() {
        return tokenRepository.findAll();
    }

}