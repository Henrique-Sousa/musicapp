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
    @Query(value = "SELECT * FROM \"user\" WHERE user_name = :userName", nativeQuery = true)
    Optional<User> findByUserName(String userName);

    @Query(value = "SELECT * FROM \"user\" WHERE uuid = :uuid", nativeQuery = true)
    Optional<User> findByUuid(UUID uuid);

    @Query(value = "SELECT u from User u WHERE u.userName like %:userName% AND u.name like %:name%")
    List<User> findAllWithPagination(String userName, String name, Pageable page);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM \"user\" WHERE user_name = :userName", nativeQuery = true)
    void deleteByUserName(String userName);
}
