package entertain_me.app.model.Anime;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="anime_streaming")
public class AnimeStreaming {

    @Id
    @Column(name = "id_anime_streaming")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anime", referencedColumnName = "id_anime", nullable = false)
    private Anime anime;

    @Column(name = "name")
    private String name;

    @Column(name ="url")
    private String url;

    public AnimeStreaming(Long animeId, String name, String url) {
        this.anime = new Anime(animeId);
        this.name = name;
        this.url = url;
    }

}
