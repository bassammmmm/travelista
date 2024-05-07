package sys.airline.airline_apis.config;

import io.micrometer.common.lang.NonNull;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sys.airline.airline_apis.services.JwtService;
import sys.airline.airline_apis.services.UserInfoService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserInfoService userInfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        LOGGER.info("JwtAuthFilter invoked");
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Request Method: " + request.getMethod());
        String authHeader = request.getHeader("Authorization");
        String jwt=null;
        String userEmail=null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // after "Bearer"
            userEmail = jwtService.extractUsername(jwt);//extracting userEmail from JWT token //pass to next filter
            System.out.println("Received JWT: " + jwt);
        }// to get Header
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) // we're checking if there is an email and the user is not authenticated yet be if authenticated it won't need this process
        {
            UserDetails userDetails = userInfoService.loadUserByUsername(userEmail); //userDetails from db
            if(jwtService.isTokenValid(jwt,userDetails)){//when is valid we need to update security and send request to dispatcher
                System.out.println("Token is valid for user: " + userEmail);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // we don't have credentials
                        userDetails.getAuthorities()
                ); // this is used for updating security context
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else {
                System.out.println("Token is not valid for user: " + userEmail);
            }
            if (!StringUtils.isEmpty(jwt)) {
                // Log the extracted email and JWT token
                System.out.println("Extracted Email: " + userEmail);
                System.out.println("Extracted JWT: " + jwt);
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                LOGGER.info("Authenticated user: {}", authentication.getName());
                LOGGER.info("Authorities: {}", authentication.getAuthorities());
            } else {
                LOGGER.info("No authentication found");
            }


        }
        filterChain.doFilter(request,response); //to get to next token
    }

}
