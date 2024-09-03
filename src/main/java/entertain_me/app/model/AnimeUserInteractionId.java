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
@Embeddable
public class AnimeUserInteractionId implements Serializable {

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "id_anime")
    private Long idAnime;
}