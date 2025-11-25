package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import br.com.henriquesousa.musicapp.dto.ExistingCardDTO;
import br.com.henriquesousa.musicapp.dto.FactoryDTO;
import br.com.henriquesousa.musicapp.dto.NewCardDTO;
import br.com.henriquesousa.musicapp.dto.SuccessDTO;
import br.com.henriquesousa.musicapp.entity.Card;
import br.com.henriquesousa.musicapp.service.CardService;
import br.com.henriquesousa.musicapp.service.exception.CardNotCreatedException;
import br.com.henriquesousa.musicapp.service.exception.CardNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cards")
@Tag(name = "card", description = "services to manage cards")
public class CardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private CardService cardService;

    @GetMapping
    @Operation(summary = "list cards", description = "list all cards")
    @ApiResponse(responseCode = "200", description = "cards successfully retrieved")
    public ResponseEntity<List<ExistingCardDTO>> list() {
        List<Card> cards = cardService.list(); 
        List<ExistingCardDTO> cardResponses = new ArrayList<>();
        for (var card : cards) {
            ExistingCardDTO cardResponse = FactoryDTO.entityToDTO(card);
            cardResponses.add(cardResponse);
        }
        return ResponseEntity.ok(cardResponses);
    }

    @PostMapping
    @Operation(summary = "create a new card", description = "creates a new card with a question and an answer")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", description = "card successfully created",
            content = @Content(examples = @ExampleObject(name = "success", value = """
                    { "success": true }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "400", description = "fields missing",
            content = @Content(examples = @ExampleObject(name = "error", value = """
                    { "question": "question-required", "error": true }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "409", description = "card not created",
            content = @Content(examples = @ExampleObject(name = "error", value = """
                    { "code": "card-not-created", "error": true }
                """)
            )
        ),
        @ApiResponse(responseCode = "500", description = "internal server error"),
    })
    public ResponseEntity<?> create(@RequestBody @Valid NewCardDTO newCardRequest) {
        Card card = FactoryDTO.newDtoToEntity(newCardRequest);
        try {
            cardService.create(card);
            LOGGER.info("card created with UUID: " + card.getUuid());
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessDTO(true));
        } catch (CardNotCreatedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            LOGGER.error("unexpected error while creating a new user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }

    @PutMapping
    @Operation(summary = "update a card", description = "update a card with new values for it's fields")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "card successfully updated",
            content = @Content(examples = @ExampleObject(name = "success", value = """
                    { "success": true }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "400", description = "fields missing",
            content = @Content(examples = @ExampleObject(name = "error", value = """
                    { "question": "question-required", "error": true }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "404", description = "card not found",
            content = @Content(examples = @ExampleObject(name = "error", value = """
                    { "code": "card-not-found", "error": true }
                """)
            )
        ),
        @ApiResponse(responseCode = "500", description = "internal server error"),
    })
    public ResponseEntity<?> update(@RequestBody @Valid ExistingCardDTO updateCardRequest) {
        Card card = FactoryDTO.existingDtoToEntity(updateCardRequest);
        try {
            cardService.update(card);
            return ResponseEntity.ok(new SuccessDTO(true));
        } catch (CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            LOGGER.error("unexpected error while creating a new user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "delete a card", description = "delete a card by id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "card successfully deleted",
            content = @Content(examples = @ExampleObject(name = "success", value = """
                    { "success": true }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "404", description = "card not found",
            content = @Content(examples = @ExampleObject(name = "error", value = """
                    { "code": "card-not-found", "error": true }
                """)
            )
        ),
        @ApiResponse(responseCode = "500", description = "internal server error"),
    })
    public ResponseEntity<?> delete(@PathVariable("uuid") UUID uuid) {
        Card card = new Card();
        card.setUuid(uuid);
        try {
            cardService.delete(card);
            LOGGER.info("card with UUID: " + card.getUuid() + " deleted");
            return ResponseEntity.ok(new SuccessDTO(true));
        } catch (CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            LOGGER.error("unexpected error while creating a new user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }
}
