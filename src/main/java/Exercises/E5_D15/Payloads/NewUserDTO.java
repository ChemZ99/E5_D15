package Exercises.E5_D15.Payloads;

import jakarta.validation.constraints.*;

public record NewUserDTO(
        @NotEmpty(message = "name is required")
        @Size(min = 3, max=50, message = "name must have a length beetween 3 and 50")
        String name,
        @NotEmpty(message = "surname is required")
        @Size(min = 2, max=50, message = "surname must have a length beetween 2 and 50")
        String surname,
        @NotEmpty(message = "La password è un campo obbligatorio!")
        String password,
        @NotEmpty(message = "email is required")
        @Email(message = "this email is not valid")
        String email) {}
