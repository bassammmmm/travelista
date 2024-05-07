package sys.airline.airline_apis.utils;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sys.airline.airline_apis.config.AesEncryptor;
import sys.airline.airline_apis.validators.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters")
    private String lastName;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Not a valid email")
    @UniqueEmail
    private String email;
    
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "Password Confirmation is required")
    @NotBlank(message = "Password Confirmation is required")
    private String passwordConfirmation;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must not be less than 0")
    @Max(value = 110, message = "Age must not be greater than 110")
    private int age;

    @NotNull(message = "Phone number is required")
    @NotBlank(message = "Phone number is required")
    @Size(min = 6, message = "Phone number must be at least 10 digits long")
    @Convert(converter = AesEncryptor.class)
    private String phoneNumber;
    
    private Gender gender;
    private Role role;

}
