package entertain_me.app.service;

import com.google.gson.Gson;
import entertain_me.app.component.Helper;
import entertain_me.app.dto.TranslateInfoDto;
import entertain_me.app.dto.TranslateReturnDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
public class LibreTranslateService {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private Helper helper;

    @Autowired
    private AnimeLanguagesService animeLanguagesService;

    @Autowired
    private TranslationService translationService;

    private final RestTemplate restTemplate = new RestTemplate();

    public void processTranslateRequest() {
        String apiUrl = "http://127.0.0.1:5000/translate";

        List<TranslateInfoDto> allAnimeSynopsys = animeService.getAllAnimeSynopsis();
        translateRequest(apiUrl, allAnimeSynopsys, "synopsys");

    }

    private void translateRequest(String apiUrl, List<TranslateInfoDto> list, String field){
        list.forEach(anime -> {
            log.info("Text: "+anime.q());
            TranslateInfoDto translateRequestDto = new TranslateInfoDto(anime.animeId(), anime.q(), "en", "pt");

            ResponseEntity<TranslateReturnDto> responseEntity = restTemplate.postForEntity(apiUrl, translateRequestDto, TranslateReturnDto.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK){
                TranslateReturnDto responseBody = responseEntity.getBody() ;
                log.info("Response: {}", responseEntity);
                animeLanguagesService.saveAnimeLanguage(anime.animeId(), responseBody == null ? "" : responseBody.translatedText(), field, false);
            }else{
                log.error("Status code: {}", responseEntity.getStatusCode());
                log.error("Return: {}", responseEntity);
            }

        });
    }

    public void translateAzure() throws Exception {
        translationService.translateOfficialAzure();
    }
}
