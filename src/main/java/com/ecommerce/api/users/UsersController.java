package com.ecommerce.api.users;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody UsersRequestDto userDto){
        if(usersService.existsById(userDto.email())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
        var userCreated = usersService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);

    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllUsers());
    }
}
