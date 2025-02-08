package com.server.gymServerApplication.repository.mysql;

import com.server.gymServerApplication.entity.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserrepository extends JpaRepository<User, String> {

    boolean existsUserByEmail(String email);

    Optional<User>findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByPhoneOrEmail(String phone, String email);

    boolean existsByEmailOrPhoneAndIsDeleteFalse(String key,String key2);


    boolean existsByIdIsDeleteFalse(String id);

}
