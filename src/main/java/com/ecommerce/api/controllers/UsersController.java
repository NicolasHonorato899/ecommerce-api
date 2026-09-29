package com.ecommerce.api.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.ecommerce.api.services.UsersService;
import com.ecommerce.api.dtos.UsersRequestDto;
import com.ecommerce.api.dtos.UsersResponseDto;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<UsersResponseDto> create(@Valid @RequestBody UsersRequestDto userDto){
        var userCreated = usersService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
    @GetMapping
    public ResponseEntity<List<UsersResponseDto>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllUsers());
    }
    @GetMapping("/{email}")
    public ResponseEntity<UsersResponseDto> getUser(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getUserByEmail(email));
    }
    @PutMapping("/{email}")
    public ResponseEntity<UsersResponseDto> updateUser(@PathVariable String email, @Valid @RequestBody UsersRequestDto userDto){
        var userUpdated = usersService.updateUser(email, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email){
        if(usersService.deleteByEmail(email)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
}
