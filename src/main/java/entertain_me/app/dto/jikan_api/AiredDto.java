package entertain_me.app.dto.jikan_api;

public record AiredDto(
        String from,
        String to,
        AiredFromDto propFrom,
        AiredToDto propTo,
        String string) {
}