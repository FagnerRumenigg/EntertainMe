package entertain_me.app.model.Anime;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import entertain_me.app.model.Demographic;
import entertain_me.app.model.Genre;
import entertain_me.app.model.Studio;
import entertain_me.app.model.Theme;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "anime")
public class Anime implements Serializable {

	@Id
	@Column(name = "id_anime")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_jikan")
	private Integer jikanId;

	@Column(name = "title")
    private String title;

	@Column(name = "source")
    private String source;

	@Column(name = "status")
    private String status;

	@Column(name = "age_rating")
	private String ageRating;

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
			name = "anime_genre",
			joinColumns = @JoinColumn(name = "id_anime"),
			inverseJoinColumns = @JoinColumn(name = "id_genre")
	)
	private Set<Genre> genres;

	@ManyToMany
	@JoinTable(
			name = "anime_studio",
			joinColumns = @JoinColumn(name = "id_anime"),
			inverseJoinColumns = @JoinColumn(name = "id_studio")
	)
	private Set<Studio> studios;


	@ManyToMany
	@JoinTable(
			name = "anime_theme",
			joinColumns = @JoinColumn(name = "id_anime"),
			inverseJoinColumns = @JoinColumn(name = "id_theme")
	)
	private Set<Theme> themes;

	public Anime(Long id, String title, String source, String status, String ageRating, String synopsys, Integer episodes, Integer year) {
		this.id = id;
		this.title = title;
		this.source = source;
		this.status = status;
		this.ageRating = ageRating;
		this.synopsys = synopsys;
		this.episodes = episodes;
		this.year = year;
	}

	public Anime(Long animeId) {
		this.id = animeId;
	}
}