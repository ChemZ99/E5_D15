package Exercises.E5_D15.Controllers;

import Exercises.E5_D15.Entities.User;
import Exercises.E5_D15.Exceptions.BadRequestException;
import Exercises.E5_D15.Payloads.NewUserDTO;
import Exercises.E5_D15.Payloads.UserLoginDTO;
import Exercises.E5_D15.Payloads.UserLoginSuccessDTO;
import Exercises.E5_D15.Services.AuthService;
import Exercises.E5_D15.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UserLoginSuccessDTO login(@RequestBody UserLoginDTO body){

        return new UserLoginSuccessDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public User saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return authService.registerUser(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}