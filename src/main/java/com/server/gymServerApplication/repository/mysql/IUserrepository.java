package com.server.gymServerApplication.repository.mysql;

import com.server.gymServerApplication.entity.mysql.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserrepository extends JpaRepository<User, String> {

    boolean existsUserByEmail(String email);

    Optional<User>findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByPhoneOrEmail(String phone, String email);

    @Query("SELECT u.password FROM User u WHERE u.email = :email")
    Optional<String> findPasswordByEmail(@Param("email") String email);

    boolean existsByEmailOrPhoneAndIsDeleteFalse(String key, String key2);

}
