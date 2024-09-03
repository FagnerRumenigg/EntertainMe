package entertain_me.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "anime_user_interactions")
public class AnimeUserInteractions implements Serializable {

    @EmbeddedId
    private AnimeUserInteractionId id;

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "id_user")
    private User userId;

    @ManyToOne
    @MapsId("idAnime")
    @JoinColumn(name = "id_anime")
    private Anime animeId;

    @Column(name = "rating_score", nullable = false)
    private short rating;

    @Column(name = "no_interest")
    private boolean noInterest;

    @Column(name = "watched")
    private boolean watched;
}