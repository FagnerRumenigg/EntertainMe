package entertain_me.app.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	@Column(name = "jikan_id")
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

	@Column(name = "demographics")
    private List<String> demographics;

	@Column(name = "studios")
	private List<String> studios;

	@Column(name = "genres")
    private List<String> genres;
}