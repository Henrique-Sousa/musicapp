package br.com.henriquesousa.musicapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.henriquesousa.musicapp.entity.User;
import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from \"user\" where user_name = :userName", nativeQuery = true)
    Optional<User> findByUserName(String userName);

    @Query(value = "select * from \"user\" where uuid = :uuid", nativeQuery = true)
    Optional<User> findByUuid(UUID uuid);

    @Transactional
    @Modifying
    @Query(value = "delete from \"user\" where user_name = :userName", nativeQuery = true)
    void deleteByUserName(String userName);
}
