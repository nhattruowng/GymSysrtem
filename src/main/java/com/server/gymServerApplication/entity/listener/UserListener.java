package com.server.gymServerApplication.entity.listener;


import com.server.gymServerApplication.entity.User;
import com.server.gymServerApplication.utils.OtherFunctions;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserListener {

    @PrePersist
    @PreUpdate
    public void init(User user) {
        try {
            user.setAvata(OtherFunctions.UploadImg("avatadf.jpg"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
