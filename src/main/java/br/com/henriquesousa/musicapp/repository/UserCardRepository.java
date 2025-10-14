package br.com.henriquesousa.musicapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.henriquesousa.musicapp.entity.UserCard;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
}
