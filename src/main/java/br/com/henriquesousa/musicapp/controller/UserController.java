package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.henriquesousa.musicapp.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {

    private List<User> users = new ArrayList<>(); 

    public UserController() {
        User joao, maria;

        joao = new User();
        maria = new User();

        joao.name = "Joao";
        joao.id = "1";

        maria.name = "Maria";
        maria.id = "2";
        
        users.add(joao);
        users.add(maria);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User newUser) {
        if (users.stream().anyMatch(user -> user.id.equals(newUser.id))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            users.add(newUser);
            return ResponseEntity.ok(newUser);
        }
    }

    @PutMapping
    // public ResponseEntity<User> update(@RequestBody User updatedUser) {
    public void update(@RequestBody User updatedUser) {
        // boolean inserted;
        users.forEach(user -> {
            if (user.id.equals(updatedUser.id)) {
                user.name = updatedUser.name;
                // inserted = true;
            } else {
                // inserted = false;
            }
        });
        // if (inserted) {
        //         return ResponseEntity.ok(updatedUser);
        // } else {
        //         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        // }
    }

    @DeleteMapping("/id/{id}")
    // TODO: se nao tiver um usuario com esse id, retornar NOT FOUND
    public void delete(@PathVariable("id") String id) {
        users.removeIf(user -> user.id.equals(id));
    }

    // @DeleteMapping()
    // public void delete(@RequestParam String id) {
    //     users.removeIf(user -> user.id.equals(id));
    // }

}
