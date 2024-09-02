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
@Table(name = "demographic")
public class Demographic implements Serializable {
    @Id
    @Column(name = "id_demographic")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDemographic;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "demographics")
    private Set<Anime> animes;

    public Demographic(String name) {
        this.name = name;
    }
}
