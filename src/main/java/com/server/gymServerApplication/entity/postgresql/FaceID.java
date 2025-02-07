package com.server.gymServerApplication.entity.postgresql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FaceID {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

}
