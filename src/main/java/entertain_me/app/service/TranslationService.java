package entertain_me.app.service;

import com.google.gson.Gson;
import entertain_me.app.dto.TranslateInfoDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Log4j2
@Service
public class TranslationService {

    @Value("${translate.key:#{null}}")
    private String apiKey;

    @Value("${translate.location:#{null}}")
    private String location;

    @Autowired
    private AnimeLanguagesService animeLanguagesService;

    @Autowired
    private AnimeService animeService;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final int MAX_LIMIT_CHARACTER = 35000;


    public void translateOfficialAzure() throws InterruptedException {
        String url = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=en&to=pt";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Ocp-Apim-Subscription-Key", apiKey);
        headers.set("Ocp-Apim-Subscription-Region", location);

        List<TranslateInfoDto> allAnimeSynopsys = getAnimeSynopsysLimit();
        translateRequest(url, allAnimeSynopsys, headers);
    }
    private void translateRequest(String apiUrl, List<TranslateInfoDto> list, HttpHeaders headers) throws InterruptedException {
        Gson gson = new Gson();
        for (TranslateInfoDto anime : list) {
            log.info("Anime Processing: {}", anime.animeId());
            String textToTranslate = anime.q();
            if (isTextEmpty(textToTranslate)) {
                log.warn("Empty text to translate, anime ID: {}", anime.animeId());
                continue;
            }

            String requestBody = createRequestBody(textToTranslate, gson);

            ResponseEntity<List<Map<String, Object>>> responseEntity = sendTranslationRequest(apiUrl, requestBody, headers);

            processTranslationResponse(responseEntity, anime);


        }
    }

    private boolean isTextEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    private String createRequestBody(String textToTranslate, Gson gson) {
        Map<String, String> textMap = Map.of("text", textToTranslate);
        return gson.toJson(Collections.singletonList(textMap));
    }

    private ResponseEntity<List<Map<String, Object>>> sendTranslationRequest(String apiUrl, String requestBody, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(apiUrl, HttpMethod.POST, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {});
    }

    private void processTranslationResponse(ResponseEntity<List<Map<String, Object>>> responseEntity, TranslateInfoDto anime) throws InterruptedException {

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            List<Map<String, Object>> responseBody = responseEntity.getBody();

            if (responseBody != null && !responseBody.isEmpty()) {
                responseBody.forEach(translationResponse -> {
                    List<Map<String, String>> translations = (List<Map<String, String>>) translationResponse.get("translations");
                    if (translations != null && !translations.isEmpty()) {
                        translations.forEach(translation -> {
                            String translatedText = translation.get("text");

                            animeLanguagesService.saveAnimeLanguage(anime.animeId(), translatedText, "synopsys", true);
                        });
                    } else {
                        log.info("No translations found for anime ID: {}", anime.animeId());
                    }
                });
            } else {
                log.info("Translation response is empty for anime ID: {}", anime.animeId());
            }
        } else {
            log.error("Failed to translate, code: {}", responseEntity.getStatusCode());
        }
        Thread.sleep(1500);
    }

    private List<TranslateInfoDto> getAnimeSynopsysLimit() {
        List<TranslateInfoDto> allAnimeSynopsys = animeService.getAllAnimeSynopsis();
        List<TranslateInfoDto> listLimited = new ArrayList<>();
        int[] characterCount = {0};

        allAnimeSynopsys.stream()
                .filter(anime -> anime.q() != null)
                .takeWhile(anime -> {
                    int synopsysSize = anime.q().length();

                    if (characterCount[0] + synopsysSize <= MAX_LIMIT_CHARACTER) {
                        characterCount[0] += synopsysSize;
                        return true;
                    } else {
                        return false;
                    }
                })
                .forEach(listLimited::add);
            log.info("Will be processed {} animes", listLimited.size());
        return listLimited;
    }

}