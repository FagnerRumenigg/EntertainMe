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
@Table(name = "theme")
public class Theme {
    @Id
    @Column(name = "id_theme")
    private UUID idTheme;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "themes")
    private Set<Anime> animes;
}
