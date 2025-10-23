package br.com.henriquesousa.musicapp.entity;

import java.util.ArrayList;
import java.util.List;

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

    @Column
    private String question;

    @Column
    private String answer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card")
    private List<UserCard> deck = new ArrayList<>();

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
}
