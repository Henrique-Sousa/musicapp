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

import br.com.henriquesousa.musicapp.dto.ErrorDTO;
import br.com.henriquesousa.musicapp.dto.FactoryDTO;
import br.com.henriquesousa.musicapp.dto.UserRequestDTO;
import br.com.henriquesousa.musicapp.dto.UserResponseDTO;
import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.service.UserService;
import br.com.henriquesousa.musicapp.service.exception.UserNotCreatedException;
import br.com.henriquesousa.musicapp.service.exception.UserNotFoundException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list() {
        // TODO: fazer try/catch pro caso de o banco de dados nao responder?
        List<User> users = userService.list(); 
        List<UserResponseDTO> userResponses = new ArrayList<>();
        for (var user : users) {
            UserResponseDTO userResponse = FactoryDTO.entityToDTO(user);
            userResponses.add(userResponse);
        }
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping
    // TODO: deveria fazer todos os DTO herdarem de uma classe DTO?
    // assim poderia retornar ResponseEntity<? extends DTO>
    public ResponseEntity<?> create(@RequestBody UserRequestDTO newUserRequest) {
        User user = FactoryDTO.dtoToEntity(newUserRequest);
        try {
            userService.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(FactoryDTO.entityToDTO(user));
        } catch (UserNotCreatedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("error", true));
        }
    }

    // TODO: colocar userName como path parameter? (PUT users/userName)
    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserRequestDTO updateUserRequest) {
        User user = FactoryDTO.dtoToEntity(updateUserRequest);
        try {
            userService.update(user);
            UserResponseDTO updatedUserResponse = FactoryDTO.entityToDTO(user);
            return ResponseEntity.ok(updatedUserResponse);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> delete(@PathVariable("userName") String userName) {
        User user = new User();
        user.setUserName(userName);
        try {
            userService.delete(user);
            UserResponseDTO deletedUserResponse = FactoryDTO.entityToDTO(user);
            return ResponseEntity.ok(deletedUserResponse);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }
}
