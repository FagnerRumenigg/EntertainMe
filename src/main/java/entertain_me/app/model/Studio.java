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
@Table(name = "studio")
public class Studio {
    @Id
    @Column(name = "id_studio")
    private UUID idStudio;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "studios")
    private Set<Anime> animes;

    public Studio(UUID idStudio, String name) {
        this.idStudio = idStudio;
        this.name = name;
    }
}
