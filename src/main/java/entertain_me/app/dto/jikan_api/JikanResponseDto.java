package entertain_me.app.dto.jikan_api;

import java.util.List;

public record JikanResponseDto(
        List<JikanResponseDataDto> data
) {}
