package entertain_me.app.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "anime")
public class Anime {

	@Id
	@Column(name = "id_anime")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@UuidGenerator
	private UUID id;

	@Column(name = "id_jikan")
	private Integer jikanId;

	@Column(name = "title")
    private String title;

	@Column(name = "source")
    private String source;

	@Column(name = "status")
    private String status;

	@Column(name = "synopsys")
    private String synopsys;

	@Column(name = "episodes")
    private Integer episodes;

	@Column(name = "release_year")
    private Integer year;

	@ManyToMany
	@JoinTable(
			name = "anime_demographic",
			joinColumns = @JoinColumn(name = "id_anime"),
			inverseJoinColumns = @JoinColumn(name = "id_demographic")
	)
    private Set<Demographic> demographics;

	@ManyToMany
	@JoinTable(
			name = "anime_studio",
			joinColumns = @JoinColumn(name = "id_anime"),
			inverseJoinColumns = @JoinColumn(name = "id_studio")
	)
	private Set<Studio> studios;

	@ManyToMany
	@JoinTable(
			name = "anime_genre",
			joinColumns = @JoinColumn(name = "id_anime"),
			inverseJoinColumns = @JoinColumn(name = "id_genre")
	)
	private Set<Genre> genres;

	public Anime(UUID id, String title, String source, String status, String synopsys, Integer episodes, Integer year) {
		this.id = id;
		this.title = title;
		this.source = source;
		this.status = status;
		this.synopsys = synopsys;
		this.episodes = episodes;
		this.year = year;
	}
}