package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            UserResponseDTO userResponse = new UserResponseDTO(
                    user.getUuid(),
                    user.getName(),
                    user.getUserName());
            userResponses.add(userResponse);
        }
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO newUserRequest) {
        User user = new User();
        user.setName(newUserRequest.getName());
        user.setUserName(newUserRequest.getUserName());
        Optional<User> maybeSavedUser = userService.create(user);
        if (maybeSavedUser.isPresent()) {
            User savedUser = maybeSavedUser.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new UserResponseDTO(
                        savedUser.getUuid(),
                        savedUser.getName(),
                        savedUser.getUserName()
                        )
                    );
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // TODO: colocar userName como path parameter? (PUT users/userName)
    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserRequestDTO updateUserRequest) {
        User user = new User();
        user.setName(updateUserRequest.getName());
        user.setUserName(updateUserRequest.getUserName());
        Optional<User> maybeUpdatedUser = userService.update(user);
        if (maybeUpdatedUser.isPresent()) {
            User updatedUser = maybeUpdatedUser.get();
            UserResponseDTO updatedUserResponse = new UserResponseDTO(
                    updatedUser.getUuid(),
                    updatedUser.getName(),
                    updatedUser.getUserName()
                );
            return ResponseEntity.ok(updatedUserResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<UserResponseDTO> delete(@PathVariable("userName") String userName) {
        Optional<User> maybeDeletedUser = userService.delete(userName);
        if (maybeDeletedUser.isPresent()) {
            User deletedUser = maybeDeletedUser.get();
            UserResponseDTO deletedUserResponse = new UserResponseDTO(
                    deletedUser.getUuid(),
                    deletedUser.getName(),
                    deletedUser.getUserName()
                );
            return ResponseEntity.ok(deletedUserResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
