package com.ecommerce.api.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.ecommerce.api.services.UsersService;
import com.ecommerce.api.dtos.UsersRequestDto;
import com.ecommerce.api.dtos.UsersResponseDto;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UsersRequestDto userDto){
        var userCreated = usersService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllUsers());
    }
    @GetMapping("/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getUserByEmail(email));
    }
    @PutMapping("/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email, @Valid @RequestBody UsersRequestDto userDto){
        var userUpdated = usersService.updateUser(email, userDto);
        if(userUpdated != null){
            return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        if(usersService.deleteByEmail(email)){
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
