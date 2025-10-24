package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.henriquesousa.musicapp.dto.FactoryDTO;
import br.com.henriquesousa.musicapp.dto.UserRequestDTO;
import br.com.henriquesousa.musicapp.dto.UserResponseDTO;
import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list() {
        List<User> users = userService.list(); 
        List<UserResponseDTO> userResponses = new ArrayList<>();
        for (var user : users) {
            UserResponseDTO userResponse = FactoryDTO.entityToDTO(user);
            userResponses.add(userResponse);
        }
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO newUserRequest) {
        User user = FactoryDTO.dtoToEntity(newUserRequest);
        boolean userCreated = userService.create(user);
        if(userCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body(FactoryDTO.entityToDTO(user));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // TODO: colocar userName como path parameter? (PUT users/userName)
    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserRequestDTO updateUserRequest) {
        User user = FactoryDTO.dtoToEntity(updateUserRequest);
        boolean userUpdated = userService.update(user);
        if(userUpdated) {
            UserResponseDTO updatedUserResponse = FactoryDTO.entityToDTO(user);
            return ResponseEntity.ok(updatedUserResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<UserResponseDTO> delete(@PathVariable("userName") UserRequestDTO deleteUserRequest) {
        User user = FactoryDTO.dtoToEntity(deleteUserRequest);
        boolean userDeleted = userService.delete(user);
        if (userDeleted) {
            UserResponseDTO deletedUserResponse = FactoryDTO.entityToDTO(user);
            return ResponseEntity.ok(deletedUserResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
