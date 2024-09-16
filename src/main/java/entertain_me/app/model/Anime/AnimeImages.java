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
@Table(name ="anime_images")
public class AnimeImages {

    @Id
    @Column(name = "id_anime")
    private Long idAnime;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "small_image_url")
    private String smallImageUrl;

    @Column(name = "large_image_url")
    private String largeImageUrl;
}
