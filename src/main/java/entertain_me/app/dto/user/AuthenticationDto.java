package entertain_me.app.dto.user;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDto(
        @NotNull
        String email,
        @NotNull
        String password) {}