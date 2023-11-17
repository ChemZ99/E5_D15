package Exercises.E5_D15.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventDTO(
        @NotEmpty(message = "title is required")
        @Size(min = 3, max=50, message = "title must have a length beetween 3 and 50")
        String title,
        @NotEmpty(message = "description is required")
        @Size(min = 3, max=50, message = "description must have a length beetween 3 and 50")
        String description,
        @NotEmpty(message = "date is required")
        LocalDate date,
        @NotEmpty(message = "place is required")
        @Size(min = 3, max=50, message = "place must have a length beetween 3 and 50")
        String place,
        @NotEmpty(message = "quantity of available slots are required")
        int freeslots) {}
