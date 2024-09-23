package entertain_me.app.component;

import entertain_me.app.dto.PaginationRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    public Pageable createPageable(PaginationRequestDto paginationRequestDto) {
        return PageRequest.of(paginationRequestDto.page(), paginationRequestDto.size());
    }
}
