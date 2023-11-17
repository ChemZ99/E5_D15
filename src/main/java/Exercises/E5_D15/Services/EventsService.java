package Exercises.E5_D15.Services;

import Exercises.E5_D15.Entities.Event;
import Exercises.E5_D15.Exceptions.NotFoundException;
import Exercises.E5_D15.Payloads.NewEventDTO;
import Exercises.E5_D15.Repositories.EventsRepository;
import Exercises.E5_D15.Repositories.UsersRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private UsersRepository usersRepository;
    private Cloudinary cloudinary;

    public Event save(NewEventDTO body) throws IOException {

        Event newEvent = new Event();
        newEvent.setTitle(body.title());
        newEvent.setDescription(body.description());
        newEvent.setDate(body.date());
        newEvent.setPlace(body.place());
        newEvent.setFreeslots(body.freeslots());
        eventsRepository.save(newEvent);
        return newEvent;
    }

    public Page<Event> getEvents(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return eventsRepository.findAll(pageable);
    }

    public Event findById(long id) throws NotFoundException {
        return eventsRepository.findById((int) id).orElseThrow( ()  -> new NotFoundException((int) id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException{
        Event target = this.findById(id);
        eventsRepository.delete(target);
    }

    public Event findByIdAndUpdate(long id, Event body) throws NotFoundException{
        Event target = this.findById(id);
        target.setTitle(body.getTitle());
        target.setDescription(body.getDescription());
        target.setDate(body.getDate());
        target.setPlace(body.getPlace());
        target.setFreeslots(body.getFreeslots());
        target.setImageUrl(body.getImageUrl());
        return eventsRepository.save(target);
    }
    public String uploadPicture(MultipartFile file, long id) throws IOException {
        Event target = this.findById(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        target.setImageUrl(url);
        eventsRepository.save(target);
        return "profile picture upload success";
    }
}
