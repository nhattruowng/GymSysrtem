package com.server.gymServerApplication.repository;

import com.server.gymServerApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserrepository extends JpaRepository<User, String> {
}
