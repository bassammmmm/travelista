package sys.airline.airline_apis.services;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sys.airline.airline_apis.models.User;
import sys.airline.airline_apis.repositories.UserRepository;


@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    public ResponseEntity<?> deleteAllUsers() {
        try {
            // Fetch all users
            List<User> users = userRepository.findAll();

            // Clear passwords for each user
            for (User user : users) {
                user.setPassword(null);  // or user.setPassword("")
                userRepository.save(user);
            }

            return ResponseEntity.ok().body("All passwords deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting passwords.");
        }
    }

}
