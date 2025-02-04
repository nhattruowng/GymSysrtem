package com.server.gymServerApplication.modelView.repon;

import lombok.Builder;

@Builder
public record LoginRepose(String name,
                          String email,
                          String phone,
                          byte[] avata,
                          String token) {
}
