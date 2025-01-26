package com.server.gymServerApplication.repository;

import com.server.gymServerApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserrepository extends JpaRepository<User, String> {

    boolean existsUserByEmail(String email);

    Optional<User>findByEmail(String email);

    Optional<User> findByPhone(String phone);
}
