package com.server.gymServerApplication.repository.postgresql;

import com.server.gymServerApplication.entity.postgresql.FaceID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFaceIdRepository extends JpaRepository<FaceID, String> {
    boolean existsByUser(String user);
    Optional<FaceID> findByUser(String user);

}
