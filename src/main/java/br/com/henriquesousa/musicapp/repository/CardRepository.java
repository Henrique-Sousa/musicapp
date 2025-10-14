package br.com.henriquesousa.musicapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.henriquesousa.musicapp.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}
