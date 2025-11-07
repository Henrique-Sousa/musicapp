package br.com.henriquesousa.musicapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.henriquesousa.musicapp.entity.UserCard;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    @Query(value = "select uc from UserCard uc join fetch uc.user u join fetch uc.card c where uc.uuid = :uuid")
    Optional<UserCard> findByUuid(UUID uuid);
}
