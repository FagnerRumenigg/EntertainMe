package entertain_me.app.dto.jikan_api;

public record PaginationDto(
        Integer last_visible_page,
        boolean has_next_page,
        ItemsDto items
) {}
