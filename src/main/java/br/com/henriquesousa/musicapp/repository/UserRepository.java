package br.com.henriquesousa.musicapp.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.henriquesousa.musicapp.entity.User;
import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u from User u WHERE u.userName = :userName")
    Optional<User> findByUserName(String userName);

    @Query(value = "SELECT u from User u WHERE u.uuid = :uuid")
    Optional<User> findByUuid(UUID uuid);

    @Query(value = "SELECT u from User u WHERE u.userName like %:userName% AND u.name like %:name%")
    List<User> findAllWithPagination(String userName, String name, Pageable page);

    @Transactional
    @Modifying
    @Query(value = "DELETE from User u WHERE u.userName = :userName")
    void deleteByUserName(String userName);
}
