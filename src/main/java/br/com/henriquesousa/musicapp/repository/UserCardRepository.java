package br.com.henriquesousa.musicapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.henriquesousa.musicapp.entity.UserCard;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    @Query(value = "SELECT uc from UserCard uc WHERE uc.uuid = :uuid")
    Optional<UserCard> findByUuid(UUID uuid);
}
