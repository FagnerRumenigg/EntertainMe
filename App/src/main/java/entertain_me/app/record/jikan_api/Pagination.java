package entertain_me.app.record.jikan_api;

public record Pagination(
        Integer last_visible_page,
        boolean has_next_page,
        Items items
) {}
