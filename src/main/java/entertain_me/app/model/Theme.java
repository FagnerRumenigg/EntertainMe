package entertain_me.app.model;

import entertain_me.app.model.Anime.Anime;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "theme")
public class Theme implements Serializable {
    @Id
    @Column(name = "id_theme")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTheme;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "themes")
    private Set<Anime> animes;

    public Theme( String name){
        this.name = name;
    }
}
