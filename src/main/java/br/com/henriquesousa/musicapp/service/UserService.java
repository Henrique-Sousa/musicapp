package br.com.henriquesousa.musicapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.henriquesousa.musicapp.entity.User;

@Service
public class UserService {

    private List<User> users = new ArrayList<>(); 

    public UserService() {

        User joao = new User();
        User maria = new User();

        joao.setName("Joao");
        joao.setId(1);

        maria.setName( "Maria");
        maria.setId(2);

        users.add(joao);
        users.add(maria);
    }

    public List<User> list() {
        return users;
    }

    public Optional<User> create(User newUser) {
        if (users.stream().anyMatch(user -> user.getId() == newUser.getId())) {
            return Optional.empty();
        } else {
            users.add(newUser);
            return Optional.of(newUser);
        }
    }

    public Optional<User> update(User updatedUser) {
        Optional<User> userFound = users.stream()
            .filter(user -> user.getId() == updatedUser.getId())
            .findFirst();
        if (userFound.isPresent()) {
            User user = userFound.get();
            user.setName(updatedUser.getName());
            return userFound;
        } else {
            return Optional.empty();
        }
    }

    // public Optional<User> delete(long id) {
    //     boolean removed = users.removeIf(user -> user.getId() == id);
    //     if (removed) {
    //         User userDeleted = users.
    //         return Optional.empty();
    //     } else {
    //         return Optional.empty();
    //     }
    // }
    
    public Optional<User> delete(long id) {
        // TODO: extrair essa funcao que tambem eh usada em update?
        Optional<User> userDeleted = users.stream()
            .filter(user -> user.getId() == id)
            .findFirst();
        if (userDeleted.isPresent()) {
            users.remove(userDeleted.get());
            return userDeleted;
        } else {
            return Optional.empty();
        }
    }
}
