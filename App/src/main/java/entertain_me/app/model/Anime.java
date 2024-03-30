package entertain_me.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "anime")
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
