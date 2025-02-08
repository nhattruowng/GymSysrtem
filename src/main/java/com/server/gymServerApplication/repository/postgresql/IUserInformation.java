package com.server.gymServerApplication.repository.postgresql;

import com.server.gymServerApplication.entity.postgresql.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserInformation extends JpaRepository<UserInformation, String> {
}
