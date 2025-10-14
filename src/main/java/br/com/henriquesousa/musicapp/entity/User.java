package br.com.henriquesousa.musicapp.entity;

import java.sql.Date;
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
@Table(name = "\"user\"")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    // TODO: precisa de getter/setter?
    // TODO: nao esta criando a data automaticamente
    @Column
    private Date createdAt;

    // TODO: precisa de getter/setter?
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserCard> deck = new ArrayList<>();

    // TODO: o lombok nao gera getters/setters automaticamente?
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
