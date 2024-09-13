package entertain_me.app.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TranslationService {

    @Value("${apiTranslate.key:#{null}}")
    private String apiKey;

    @Value("${apiTranslate.location:#{null}}")
    private String location;

    private final RestTemplate restTemplate = new RestTemplate();

    // This function performs a POST request.
    public String post() throws Exception {
        String url = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=en&to=pt-br";

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Ocp-Apim-Subscription-Key", "ca117f2a729f4a0bac7ab2d7c33a4efc");
        headers.set("Ocp-Apim-Subscription-Region", "eastus");
        // TODO: ADICIONAR SELECT PARA PEGAR SYNOPSY DO ANIME, ADICIONAR A UM DTO (PRECISA SER CRIADO)

        // Create request body
        String body = "[{\"Text\":\"THE LAST TEST\"}]";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        // Perform the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Check for successful response
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // TODO: SALVAR NA TABELA ANIME_LANGUAGE (ID_ANIME, LINGUAGEM, SINOPSE)
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Failed to translate text. HTTP status: " + responseEntity.getStatusCode());
        }
    }

    // This function prettifies the JSON response.
    public static String prettify(String jsonText) {
        JsonElement json = JsonParser.parseString(jsonText);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}