package entertain_me.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertain_me.app.dto.jikan_api.JikanResponseDataDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class JikanAPIService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<JikanResponseDataDto> requestAllAnimes(Integer page) throws Exception {
        String apiUrl = String.format("https://api.jikan.moe/v4/anime?page=%d", page);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        List<JikanResponseDataDto> JikanResponseDataDtoList = new ArrayList<>();
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            JsonNode dataNode = rootNode.path("data");

            if (dataNode.isArray()) {
                for (JsonNode animeNode : dataNode) {
                    JikanResponseDataDto jikanResponseDataDto = new JikanResponseDataDto(
                            animeNode.path("mal_id").asInt(),
                            animeNode.path("title").asText(),
                            animeNode.path("source").asText(),
                            animeNode.path("status").asText(),
                            animeNode.path("synopsis").asText(),
                            animeNode.path("episodes").asInt(),
                            animeNode.path("year").asInt(),
                            getNameFromJikan(animeNode, "demographics"),
                            getNameFromJikan(animeNode, "studios"),
                            getNameFromJikan(animeNode, "genres")
                    );
                    JikanResponseDataDtoList.add(jikanResponseDataDto);
                }
            }
        } else {
            throw new Exception("The connection with the jikan-api is down. Try again later.");
        }
        return JikanResponseDataDtoList;
    }

    private List<String> getNameFromJikan(JsonNode jsonNode, String jikanList) {
        List<String> genericList = new ArrayList<>();

        JsonNode genericJsonNodeList = jsonNode.path(jikanList);

        if (genericJsonNodeList.isArray()) {
            for (JsonNode genericJsonNode : genericJsonNodeList) {
                String name = genericJsonNode.path("name").asText();
                genericList.add(name);
            }
        } else {
           log.info("The jikanList is not an array or doesn't exist: " + jikanList);
        }

        return genericList;
    }

}