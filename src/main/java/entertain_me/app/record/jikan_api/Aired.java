package entertain_me.app.record.jikan_api;

public record Aired(
        String from,
        String to,
        AiredFrom propFrom,
        AiredTo propTo,
        String string) {
}