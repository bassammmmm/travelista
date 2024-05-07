package sys.airline.airline_apis.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sys.airline.airline_apis.models.AuthToken;
import sys.airline.airline_apis.repositories.TokenRepository;
import sys.airline.airline_apis.utils.UserInfoDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    public static SecretKey Key;

    @Autowired
    private TokenRepository tokenRepository;

    public JwtService() {
        String secretKey = "bc728754c30ec08faf605b34d57f2a624b330fcdc77598e7aeaba23924c059969787978676776968";
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        JwtService.Key = new SecretKeySpec(keyBytes,"HmacSHA256");//algorithm for decoding
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);// subject is username of user
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(Key).build().parseClaimsJws(token).getBody(); //extracting any claim
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();


        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))// when claim was created
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //will be available for 24 hours
                .signWith(Key)
                .compact();
    }
    public String generateRefreshToken(
            HashMap<String, Object> claims,
            UserInfoDetails userDetails
    ) {

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))// when claim was created
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //will be available for 24 hours
                .signWith(Key)
                .compact();//generate the token
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token,Claims::getExpiration).before(new Date()); //to check the token date is before today's date
    }


    public void deleteTokenByUsername(String username) {
        AuthToken token = tokenRepository.findByUsername(username);
        if (token != null) {
            tokenRepository.delete(token);
        }
    }
    public String extractTokenFromRequest(HttpServletRequest request) {
        // Extract the token from the "Authorization" header
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extract the token from the "Bearer " prefix
        }
        return null;
    }

    public void clearTokenFromSession(HttpServletRequest request) {
        request.getSession().removeAttribute("token");
    }

    public List<AuthToken> getAllTokens() {
        return tokenRepository.findAll();
    }



}
