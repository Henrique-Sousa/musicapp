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

import br.com.henriquesousa.musicapp.dto.UserDTO;
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
        List<User> userList = userService.list(); 
        List<UserResponseDTO> responseList = new ArrayList<>();
        for (var user : userList) {
            UserResponseDTO userResponse = new UserResponseDTO(
                    user.getUuid(),
                    user.getName(),
                    user.getUserName());
            responseList.add(userResponse);
        }
        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserDTO newUser) {
        User dbUser = new User();
        dbUser.setName(newUser.getName());
        dbUser.setUserName(newUser.getUserName());
        Optional<User> userCreated = userService.create(dbUser);
        if (userCreated.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new UserResponseDTO(
                        userCreated.get().getUuid(),
                        userCreated.get().getName(),
                        userCreated.get().getUserName()
                        )
                    );
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // TODO: colocar userName como path parameter? (PUT users/userName)
    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserDTO updatedUser) {
        User dbUser = new User();
        dbUser.setName(updatedUser.getName());
        dbUser.setUserName(updatedUser.getUserName());
        Optional<User> userUpdated = userService.update(dbUser);
        if (userUpdated.isPresent()) {
            User user = userUpdated.get();
            UserResponseDTO responseUser = new UserResponseDTO(
                    user.getUuid(),
                    user.getName(),
                    user.getUserName()
                );
            return ResponseEntity.ok(responseUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<UserResponseDTO> delete(@PathVariable("userName") String userName) {
        Optional<User> userDeleted = userService.delete(userName);
        if (userDeleted.isPresent()) {
            User user = userDeleted.get();
            UserResponseDTO responseUser = new UserResponseDTO(
                    user.getUuid(),
                    user.getName(),
                    user.getUserName()
                );
            return ResponseEntity.ok(responseUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
