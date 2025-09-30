package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<User> list() {
        return users;
    }

    @PostMapping
    public void create(@RequestBody User newUser) {
        if (! users.stream().anyMatch(user -> user.id.equals(newUser.id))) {
            users.add(newUser);
        }
    }

    @PutMapping
    public void update(@RequestBody User updatedUser) {
        users.forEach(user -> {
            if (user.id.equals(updatedUser.id)) {
                user.name = updatedUser.name;
            }
        });
    }

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") String id) {
        users.removeIf(user -> user.id.equals(id));
    }

    // @DeleteMapping()
    // public void delete(@RequestParam String id) {
    //     users.removeIf(user -> user.id.equals(id));
    // }

}
