package br.com.henriquesousa.musicapp.controller;

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

import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok(userService.list());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User newUser) {
        Optional<User> userCreated = userService.create(newUser);
        if (userCreated.isPresent()) {
            return ResponseEntity.ok(userCreated.get());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // TODO: colocar userName como path parameter? (PUT users/userName)
    @PutMapping
    public ResponseEntity<User> update(@RequestBody User updatedUser) {
        Optional<User> userUpdated = userService.update(updatedUser);
        if (userUpdated.isPresent()) {
            User user = userUpdated.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<User> delete(@PathVariable("userName") String userName) {
        Optional<User> userDeleted = userService.delete(userName);
        if (userDeleted.isPresent()) {
            User user = userDeleted.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
