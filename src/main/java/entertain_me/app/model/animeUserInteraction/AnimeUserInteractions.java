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
@ToString
@Table(name = "anime_user_interaction")
public class AnimeUserInteractions implements Serializable {

    @EmbeddedId
    private AnimeUserInteractionId id;

    @Column(name = "rating_score", nullable = false)
    private short rating;

    @Column(name = "no_interest")
    private Boolean noInterest;

    @Column(name = "is_watched")
    private Boolean watched;

    @Column(name = "is_watching")
    private Boolean watching;

    @Column(name = "is_my_list")
    private Boolean myList;

    @Column(name = "interaction_date")
    private LocalDateTime InteractionDate;
}
