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
@Table(name = "demographic")
public class Demographic {
    @Id
    @Column(name = "id_demographic")
    private UUID idDemographic;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "demographics")
    private Set<Anime> animes;

    public Demographic(UUID idDemographic, String name) {
        this.idDemographic = idDemographic;
        this.name = name;
    }
}
