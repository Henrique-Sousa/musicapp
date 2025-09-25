package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @DeleteMapping()
    public void delete() {
        users.removeFirst();
    }

}
