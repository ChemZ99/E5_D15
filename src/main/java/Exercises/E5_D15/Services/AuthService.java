package Exercises.E5_D15.Services;

import Exercises.E5_D15.Entities.Role;
import Exercises.E5_D15.Entities.User;
import Exercises.E5_D15.Exceptions.BadRequestException;
import Exercises.E5_D15.Exceptions.UnauthorizedException;
import Exercises.E5_D15.Payloads.NewUserDTO;
import Exercises.E5_D15.Payloads.UserLoginDTO;
import Exercises.E5_D15.Repositories.UsersRepository;
import Exercises.E5_D15.Security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private UsersRepository usersRepository;

    public String authenticateUser(UserLoginDTO body){
        User user = usersService.findByEmail(body.email());

        if(bcrypt.matches(body.password(),user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }
    public User registerUser(NewUserDTO body) throws IOException {

        usersRepository.findByEmail(body.email()).ifPresent( user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già utilizzata!");
        });

        User newUser = new User();
        newUser.setName(body.name());
        newUser.setSurname(body.surname());
        newUser.setPassword(bcrypt.encode(body.password())); // $2a$11$wQyZ17wrGu8AZeb2GCTcR.QOotbcVd9JwQnnCeqONWWP3wRi60tAO
        newUser.setEmail(body.email());
        newUser.setRole(Role.USER);
        User savedUser = usersRepository.save(newUser);
        return savedUser;
    }
}
