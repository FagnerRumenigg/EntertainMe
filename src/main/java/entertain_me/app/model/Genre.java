package entertain_me.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @Column(name = "id_genre")
    private UUID idGenre;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Anime> animes;

    public Genre(UUID idGenre, String name) {
        this.idGenre = idGenre;
        this.name = name;
    }
}
