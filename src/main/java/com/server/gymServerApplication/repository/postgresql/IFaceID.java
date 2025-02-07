package com.server.gymServerApplication.repository.postgresql;

import com.server.gymServerApplication.entity.postgresql.FaceID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFaceID extends JpaRepository<FaceID, String> {
}
