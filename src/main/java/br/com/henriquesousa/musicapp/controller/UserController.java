package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<User> list() {
        return users;
    }

    @PostMapping
    public void create(@RequestBody User user) {
        // TODO: verificar se ja nao existe usuario com esse id
        users.add(user);
    }

    @PutMapping
    public void update(@RequestBody User updatedUser) {
        users.forEach(user -> {
            if (user.id.equals(updatedUser.id)) {
                user.name = updatedUser.name;
            }
        });
    }

    @DeleteMapping()
    public void delete(String id) {
        users.removeIf(user -> user.id.equals(id));
    }

}
