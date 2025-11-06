package br.com.henriquesousa.musicapp.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "card")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Column
    private String question;

    @Column
    private String answer;

    @JsonIgnore    // TODO: remover?
    @Column
    private Timestamp createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card")
    private List<UserCard> deck = new ArrayList<>();

    public Card() {}

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Card(UUID uuid, String question, String answer) {
        this.uuid = uuid;
        this.question = question;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<UserCard> getDeck() {
        return deck;
    }

    public void setDeck(List<UserCard> deck) {
        this.deck = deck;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
