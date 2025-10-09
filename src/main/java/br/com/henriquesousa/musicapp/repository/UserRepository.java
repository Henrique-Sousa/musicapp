package br.com.henriquesousa.musicapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.henriquesousa.musicapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from \"user\" where user_name = :userName", nativeQuery = true)
    // collection?
    List<User> findByUserName(String userName);
}
