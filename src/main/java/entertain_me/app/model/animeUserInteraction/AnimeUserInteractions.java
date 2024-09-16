package entertain_me.app.model.animeUserInteraction;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "anime_user_interactions")
public class AnimeUserInteractions implements Serializable {

    @EmbeddedId
    private AnimeUserInteractionId id;

    @Column(name = "rating_score", nullable = false)
    private short rating;

    @Column(name = "no_interest")
    private boolean noInterest;

    @Column(name = "watched")
    private boolean watched;

    @Column(name = "interaction_date")
    private LocalDateTime InteractionDate;
}
