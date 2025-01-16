package com.server.gym_serverapplication.repository;

import com.server.gym_serverapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserrepository extends JpaRepository<User, String> {
}
