package com.server.gymServerApplication.modelView.repon;

import lombok.Builder;

@Builder
public record LoginRepose(String username, String email, String phone, String token) {
}
