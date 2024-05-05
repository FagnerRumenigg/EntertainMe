package entertain_me.app.dto.user;

import entertain_me.app.model.User;
import jakarta.persistence.Column;

public record AddressDto(

    Long address_id,

    Long user_id,

    String street,

    String city,

    String estate,

    String country,

    String cep
) {
}
