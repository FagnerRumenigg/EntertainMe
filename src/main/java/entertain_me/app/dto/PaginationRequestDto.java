package entertain_me.app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PaginationRequestDto(
        @Min(value = 0, message = "Page must be 0 or greater")
        int page,
        @Min(value = 1, message = "Size must be 1 or greater")
        @Max(value = 100, message = "Size must be 100 or less")
        int size) {
}
