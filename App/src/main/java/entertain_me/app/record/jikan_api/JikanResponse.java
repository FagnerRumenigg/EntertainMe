package entertain_me.app.record.jikan_api;

import java.util.List;

public record JikanResponse(
        List<JikanRequestAllRecord> data,
        Pagination pagination
) {}
