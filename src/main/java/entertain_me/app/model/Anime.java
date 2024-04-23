package entertain_me.app.model;

import java.util.List;

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
    private String id;
	
	@Column(name = "jikan_id")
	private Integer jikanId;
	
	@Column(name = "titulo")
    private String title;
	
	@Column(name = "fonte_origem")
    private String source;
	
	@Column(name = "situacao_atual")
    private String status;
	
	@Column(name = "sinopse")
    private String synopsys;
	
	@Column(name = "quantidade_episodios")
    private Integer episodes;
	
	@Column(name = "ano_lancamento")
    private Integer year;
	
	@Column(name = "demografias")
    private List<String> demographics;

	@Column(name = "estudios")
	private List<String> studios;
	
	@Column(name = "generos")
    private List<String> genres;
}