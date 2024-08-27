package entertain_me.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class AnimeUserInteractionId implements Serializable {

    @Column(name = "id_user")
    private UUID idUser;

    @Column(name = "id_anime")
    private UUID idAnime;
}
