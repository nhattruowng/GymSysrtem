package com.server.gymServerApplication.modelView.reques;

import jakarta.validation.constraints.NotNull;

public record RegisUser(@NotNull String email,@NotNull String password) {
}
