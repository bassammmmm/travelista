package sys.airline.airline_apis.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sys.airline.airline_apis.config.AesEncryptor;
import sys.airline.airline_apis.utils.Gender;
import sys.airline.airline_apis.utils.Role;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;

    @Column(unique = true, nullable = false)
    private String email;

    @Convert(converter = AesEncryptor.class)
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;
    private String passwordConfirmation;

}
