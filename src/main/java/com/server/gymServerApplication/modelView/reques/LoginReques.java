package com.server.gymServerApplication.modelView.reques;


import jakarta.validation.constraints.NotNull;

public record LoginReques(@NotNull(message = "email hoac sdt trong!") String keyLogin,
                          @NotNull(message = "mat khau khong the trong!") String password) {
}
