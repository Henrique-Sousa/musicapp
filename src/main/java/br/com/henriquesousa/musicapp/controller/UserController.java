package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.henriquesousa.musicapp.dto.ErrorDTO;
import br.com.henriquesousa.musicapp.dto.ExistingUserDTO;
import br.com.henriquesousa.musicapp.dto.FactoryDTO;
import br.com.henriquesousa.musicapp.dto.NewUserDTO;
import br.com.henriquesousa.musicapp.dto.SuccessDTO;
import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.service.UserService;
import br.com.henriquesousa.musicapp.service.exception.UserNotCreatedException;
import br.com.henriquesousa.musicapp.service.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "user", description = "services to manage users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "list users", description = "list all users")
    @ApiResponse(responseCode = "200", description = "users successfully retrieved")
    public ResponseEntity<List<ExistingUserDTO>> list(
            @RequestParam(defaultValue = "") String userName,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "1") int pageNumber
        ) {
        // TODO: fazer try/catch pro caso de o banco de dados nao responder?
        List<User> users = userService.findAllWithPagination(userName, name, PageRequest.of(pageNumber - 1, 2)); 
        List<ExistingUserDTO> userResponses = new ArrayList<>();
        for (var user : users) {
            ExistingUserDTO userResponse = FactoryDTO.entityToDTO(user);
            userResponses.add(userResponse);
        }
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping
    // TODO: deveria fazer todos os DTO herdarem de uma classe DTO?
    // assim poderia retornar ResponseEntity<? extends DTO>
    @Operation(summary = "create a new user", description = "creates a new user with a name and username")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", description = "user successfully created",
            content = @Content(examples = @ExampleObject(name = "success", value = """
                    { "success": true }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "400", description = "fields missing",
            content = @Content(examples = @ExampleObject(name = "error", value = """
                    { "userName": "username-required", "error": true }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "409", description = "user not created",
            content = @Content(examples = @ExampleObject(name = "error", value = """
                    { "code": "user-not-created", "error": true }
                """)
            )
        ),
        @ApiResponse(responseCode = "500", description = "internal server error"),
    })
    public ResponseEntity<?> create(@RequestBody @Valid NewUserDTO newUserRequest) {
        User user = FactoryDTO.newDtoToEntity(newUserRequest);
        try {
            userService.create(user);
            LOGGER.info("user created" + 
                    " with username: " + user.getUserName() +
                    " and UUID: " + user.getUuid());
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessDTO(true));
        } catch (UserNotCreatedException e) {
            LOGGER.debug("user not created exception", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            LOGGER.error("unexpected error while creating a new user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }

    // TODO: colocar userName como path parameter? (PUT users/userName)
    @PutMapping
    @Operation(summary = "update a user", description = "update a user with new values for it's fields")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "user successfully updated",
            content = @Content(examples = @ExampleObject(name = "success", value = """
                    { "success": true }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "400", description = "fields missing",
            content = @Content(examples = @ExampleObject(name = "error", value = """
                    { "name": "name-required", "error": true }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "404", description = "user not found",
            content = @Content(examples = @ExampleObject(name = "error", value = """
                    { "code": "user-not-found", "error": true }
                """)
            )
        ),
        @ApiResponse(responseCode = "500", description = "internal server error"),
    })
    // TODO: trocar pra existingUserDTO e fazer o update por uuid
    public ResponseEntity<?> update(@RequestBody @Valid NewUserDTO updateUserRequest) {
        User user = FactoryDTO.newDtoToEntity(updateUserRequest);
        try {
            userService.update(user);
            LOGGER.debug("user " + 
                    " with username: " + user.getUserName() +
                    " and UUID: " + user.getUuid() +
                    " updated");
            return ResponseEntity.ok(new SuccessDTO(true));
        } catch (UserNotFoundException e) {
            LOGGER.debug("user " + 
                    " with username: " + user.getUserName() +
                    " and UUID: " + user.getUuid() +
                    " not updated - user not found exception", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            LOGGER.error("unexpected error while updating a new user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }

    @DeleteMapping("/{userName}")
    @Operation(summary = "delete a user", description = "delete a user by id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "user successfully deleted",
            content = @Content(examples = @ExampleObject(name = "success", value = """
                    { "success": true }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "404", description = "user not found",
            content = @Content(examples = @ExampleObject(name = "error", value = """
                    { "code": "user-not-found", "error": true }
                """)
            )
        ),
        @ApiResponse(responseCode = "500", description = "internal server error"),
    })
    public ResponseEntity<?> delete(@PathVariable("userName") String userName) {
        User user = new User();
        user.setUserName(userName);
        try {
            userService.delete(user);
            LOGGER.info("user " + 
                    " with username: " + user.getUserName() +
                    " and UUID: " + user.getUuid() +
                    " deleted");
            return ResponseEntity.ok(new SuccessDTO(true));
        } catch (UserNotFoundException e) {
            LOGGER.debug("user " + 
                    " with username: " + user.getUserName() +
                    " and UUID: " + user.getUuid() +
                    " not deleted - user not found exception", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            LOGGER.error("unexpected error while deleting a new user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }
}
