package com.ecommerce.api.users;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@Transactional
public class UsersService {

    @Autowired
    private IUsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsersResponseDto> getAllUsers() {
        return usersRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public UsersResponseDto getUserByEmail(String email){
        var user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return toResponseDto(user);
    }

    public boolean existsByEmail(String email) {
        return usersRepository.existsById(email);
    }

    public UsersResponseDto createUser(UsersRequestDto userDto){
        if(usersRepository.existsById(userDto.email())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }

        var user = new UsersModel();
        user.setEmail(userDto.email());
        user.setName(userDto.name());
        user.setAddress(userDto.address());
        user.setPassword(passwordEncoder.encode(userDto.password()));

        var saved = usersRepository.save(user);
        return toResponseDto(saved);
    }

    public UsersResponseDto updateUser(String email, UsersRequestDto userDto){
        var user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setName(userDto.name());
        user.setAddress(userDto.address());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        var updated = usersRepository.save(user);
        return toResponseDto(updated);
    }

    public boolean deleteByEmail(String email){
        var user = usersRepository.findByEmail(email);
        if(user.isPresent()){
            usersRepository.delete(user.get());
            return true;
        }
        return false;
    }

    private UsersResponseDto toResponseDto(UsersModel user){
        return new UsersResponseDto(
                user.getEmail(),
                user.getName(),
                user.getAddress());
    }
}
