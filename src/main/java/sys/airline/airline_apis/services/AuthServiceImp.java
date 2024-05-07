package sys.airline.airline_apis.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sys.airline.airline_apis.config.CustomPasswordEncoder;
import sys.airline.airline_apis.models.AuthToken;
import sys.airline.airline_apis.models.User;
import sys.airline.airline_apis.repositories.TokenRepository;
import sys.airline.airline_apis.repositories.UserRepository;
import sys.airline.airline_apis.utils.AuthRequest;
import sys.airline.airline_apis.utils.AuthenticationResponse;
import sys.airline.airline_apis.utils.RegisterRequest;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImp {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenRepository tokenRepository;


    public AuthenticationResponse addUser(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .age(request.getAge())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .passwordConfirmation(customPasswordEncoder.encode(request.getPasswordConfirmation()))
                .password(customPasswordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        user = userRepository.save(user);

        return new AuthenticationResponse("User registered successfully", user);
    }
    public AuthenticationResponse logIn(AuthRequest signInRequest) {
        try {
            
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getEmail(),
                            signInRequest.getPassword()
                    )
            );
            User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("This user is not found."));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Email: " + signInRequest.getEmail());
            System.out.println("Password: " + signInRequest.getPassword());
            String token = jwtService.generateToken(signInRequest.getEmail());


            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);

            AuthToken authToken = new AuthToken();
            authToken.setToken(token);
            authToken.setUsername(signInRequest.getEmail());
            tokenRepository.save(authToken);

            return new AuthenticationResponse(token, user);
        } catch (BadCredentialsException e) {
            return null;
        }

    }

}
