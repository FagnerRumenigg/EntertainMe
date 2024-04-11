package entertain_me.app.model;

import java.util.List;

import jakarta.persistence.Entity;
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
    private String id;
    private String titulo;
    private String fonteDeOrigem;
    private String situacaoAtual;
    private String sinopse;
    private Integer jikanId;
    private Integer quantidadeEpisodios;
    private Integer anoLancamento;
    private List<String> estudios;
    private List<String> generos;
    private List<String> demografias;
}