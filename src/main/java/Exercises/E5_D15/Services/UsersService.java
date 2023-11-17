package Exercises.E5_D15.Services;

import Exercises.E5_D15.Entities.User;
import Exercises.E5_D15.Exceptions.NotFoundException;
import Exercises.E5_D15.Payloads.NewUserDTO;
import com.cloudinary.Cloudinary;
import Exercises.E5_D15.Repositories.UsersRepository;
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
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    public User save(NewUserDTO body) throws IOException {

        User newUser = new User();
        newUser.setName(body.name());
        newUser.setSurname(body.surname());
        newUser.setEmail(body.email());
        usersRepository.save(newUser);
        return newUser;
    }

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return usersRepository.findAll(pageable);
    }

    public User findById(long id) throws NotFoundException {
        return usersRepository.findById((int) id).orElseThrow( ()  -> new NotFoundException((int) id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException{
        User found = this.findById(id);
        usersRepository.delete(found);
    }

    public User findByIdAndUpdate(long id, User body) throws NotFoundException{
        User found = this.findById(id);
        found.setSurname(body.getSurname());
        found.setName(body.getName());
        found.setEmail(body.getEmail());
        found.setPassword(body.getPassword());
        return usersRepository.save(found);
    }

    public User findByEmail(String email){
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

}
