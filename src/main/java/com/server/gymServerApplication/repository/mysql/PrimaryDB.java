package com.server.gymServerApplication.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PrimaryDB<T, D> extends JpaRepository<T, D> {
}
