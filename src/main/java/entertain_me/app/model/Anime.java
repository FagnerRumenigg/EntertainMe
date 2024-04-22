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
    private String titulo;
	
	@Column(name = "fonte_origem")
    private String fonteDeOrigem;
	
	@Column(name = "situacao_atual")
    private String situacaoAtual;
	
	@Column(name = "sinopse")
    private String sinopse;
	
	@Column(name = "quantidade_episodios")
    private Integer quantidadeEpisodios;
	
	@Column(name = "ano_lancamento")
    private Integer anoLancamento;
	
	@Column(name = "demografias")
    private List<String> demografias;

	@Column(name = "estudios")
	private List<String> estudios;
	
	@Column(name = "generos")
    private List<String> generos;
}