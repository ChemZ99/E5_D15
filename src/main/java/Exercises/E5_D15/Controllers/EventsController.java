package Exercises.E5_D15.Controllers;

import Exercises.E5_D15.Entities.Event;
import Exercises.E5_D15.Exceptions.BadRequestException;
import Exercises.E5_D15.Payloads.NewEventDTO;
import Exercises.E5_D15.Services.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    private EventsService eventsService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ORGANIZER','USER')")
    public Page<Event> getDevices(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
        return eventsService.getEvents(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event saveDevice(@RequestBody @Validated NewEventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return eventsService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ORGANIZER','USER')")
    public Event findEventById(@PathVariable long id) {
        return eventsService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event findEventByIdAndUpdate(@PathVariable long id, @RequestBody Event body) {
        return eventsService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public void findEventByIdAndDelete(@PathVariable long id) {
        eventsService.findByIdAndDelete(id);
    }

}
