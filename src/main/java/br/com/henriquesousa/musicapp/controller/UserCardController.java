package br.com.henriquesousa.musicapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.henriquesousa.musicapp.entity.UserCard;
import br.com.henriquesousa.musicapp.service.UserCardService;

@RestController
@RequestMapping("/user-cards")
public class UserCardController {

    @Autowired
    private UserCardService userCardService;

    @GetMapping
    public ResponseEntity<List<UserCard>> list() {
        return ResponseEntity.ok(userCardService.list());
    }
}
