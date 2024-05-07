package sys.airline.airline_apis.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sys.airline.airline_apis.models.AuthToken;

public interface TokenRepository extends JpaRepository<AuthToken, Long> {
    AuthToken findByUsername(String username);
}
