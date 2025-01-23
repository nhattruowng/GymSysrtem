package com.server.gymServerApplication.modelView.repon;


import lombok.Builder;

@Builder
public record UserRepo(String userId, String username, String password, String email, byte[] avatar) {
}
