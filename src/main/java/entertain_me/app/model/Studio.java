package entertain_me.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "studio")
public class Studio implements Serializable {
    @Id
    @Column(name = "id_studio")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStudio;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "studios")
    private Set<Anime> animes;

    public Studio(String name) {
        this.name = name;
    }
}
