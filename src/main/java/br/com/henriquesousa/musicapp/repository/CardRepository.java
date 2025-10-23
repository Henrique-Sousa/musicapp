package br.com.henriquesousa.musicapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.henriquesousa.musicapp.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(value = "select * from card where uuid = :uuid", nativeQuery = true)
    Optional<Card> findByUuid(UUID uuid);
}
