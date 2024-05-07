package sys.airline.airline_apis.utils;

import lombok.Getter;
import sys.airline.airline_apis.models.User;

@Getter
public class AuthenticationResponse {
    private final String token;
    private final User user;
    public AuthenticationResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

}