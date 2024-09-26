package entertain_me.app.model.Anime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="anime_languages")
public class AnimeLanguages {

    @Id
    @Column(name = "id_anime")
    private Long idAnime;

    @Column(name = "language")
    private String language;

    @Column(name = "status")
    private String status;

    @Column(name = "age_rating")
    private String ageRating;

    @Column(name = "synopsys")
    private String synopsys;

    @Column(name = "official_translate")
    private boolean officialTranslate;
}
