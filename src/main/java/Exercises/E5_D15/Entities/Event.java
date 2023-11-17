package Exercises.E5_D15.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    @Column
    private long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private LocalDate date;
    @Column
    private String place;
    @Column
    private int freeslots;
    @Column
    private String imageUrl;

}
