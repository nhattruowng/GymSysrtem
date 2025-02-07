package com.server.gymServerApplication.modelView.repon;

import jakarta.validation.constraints.NotNull;

public record ChangePassword(@NotNull String oldPassword, @NotNull String newPassword) {
}
