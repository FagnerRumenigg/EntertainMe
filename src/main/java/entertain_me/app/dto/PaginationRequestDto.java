package entertain_me.app.dto;

public record PaginationRequestDto(
        int page,
        int size,
        String sort,
        String direction
) {
}
