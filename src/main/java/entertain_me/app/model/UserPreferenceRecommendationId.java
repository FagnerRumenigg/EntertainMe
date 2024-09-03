package entertain_me.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserPreferenceRecommendationId  implements Serializable {

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "id_demographic")
    private Long idDemographic;

    @Column(name = "id_genre")
    private Long idGenre;

    @Column(name = "id_studio")
    private Long idStudio;

    @Column(name = "id_theme")
    private Long id_theme;
}