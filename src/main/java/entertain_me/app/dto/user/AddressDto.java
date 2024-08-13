package entertain_me.app.dto.user;

import entertain_me.app.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

import java.util.UUID;

@Schema(name = "Address")
public record AddressDto(

    @Schema(name = "addressId")
    UUID addressId,
    @Schema(name = "userId")
    UUID userId,
    @Schema(name = "street")
    String street,
    @Schema(name = "city")
    String city,
    @Schema(name = "estate")
    String estate,
    @Schema(name = "country")
    String country,
    @Schema(name = "cep")
    String cep
) {
}
