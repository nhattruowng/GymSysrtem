package com.server.gymServerApplication.modelView.repon;


public record LoginRepose(String name,
                          String email,
                          String phone,
                          byte[] avata,
                          String token) {
}
