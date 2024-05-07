package sys.airline.airline_apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("sys.airline.airline_apis.models")
public class AirlineApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlineApisApplication.class, args);
	}

}
