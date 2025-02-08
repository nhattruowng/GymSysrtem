package com.server.gymServerApplication.entity.postgresql;

import com.server.gymServerApplication.entity.baseObj.BaseObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(indexes = {
        @Index(name = "idx_user_face_id", columnList = "user_id")
})
@Getter
@Setter
public class FaceID extends BaseObject{

    @Column(name = "user_id", nullable = false, unique = true)
    private String user;

    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] face;
}
