package com.ecommerce.api.users;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UsersModel usersModel){
        var user = this.userRepository.findByEmail(usersModel.getEmail());

    if(user != null){
        System.out.println("User already exists:");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
    }

    var userCreatd = this.userRepository.save(usersModel);
    return ResponseEntity.status(HttpStatus.OK).body(userCreatd);

    }
}
