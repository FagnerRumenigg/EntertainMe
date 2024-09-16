package entertain_me.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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

        return buildJikanResponseDataDto(responseEntity);
    }



    public List<JikanResponseDataDto> requestTopAnimes() throws Exception {
        String apiUrl = "https://api.jikan.moe/v4/top/anime";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        return buildJikanResponseDataDto(responseEntity);
    }

    private List<JikanResponseDataDto> buildJikanResponseDataDto(ResponseEntity<String> responseEntity) throws Exception {
        List<JikanResponseDataDto> JikanResponseDataDtoList = new ArrayList<>();

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode dataReturn = mapper.readTree(responseBody);
            JsonNode dataAnimeList = dataReturn.path("data");

            if (dataAnimeList.isArray()) {
                for (JsonNode anime : dataAnimeList) {
                    JikanResponseDataDto jikanResponseDataDto = new JikanResponseDataDto(
                            anime.path("mal_id").asInt(),
                            anime.path("title").asText(),
                            anime.path("source").asText(),
                            anime.path("status").asText(),
                            anime.path("rating").asText(),
                            anime.path("synopsis").asText(),
                            anime.path("episodes").asInt(),
                            anime.path("year").asInt(),
                            getNameFromJikan(anime, "demographics"),
                            getNameFromJikan(anime, "studios"),
                            getNameFromJikan(anime, "genres"),
                            getNameFromJikan(anime, "themes"),
                            getImageFromJikan(anime, "webp", "image_url"),
                            getImageFromJikan(anime, "webp", "small_image_url"),
                            getImageFromJikan(anime, "webp", "large_image_url")
                    );
                    JikanResponseDataDtoList.add(jikanResponseDataDto);
                }
            }
        } else {
            throw new Exception("The connection with the jikan-api is down. Try again later.");
        }
        return JikanResponseDataDtoList;
    }

    private String getImageFromJikan(JsonNode jsonNode, String format, String property) {
        JsonNode imagesList = jsonNode.path("images");
        JsonNode formatNode = imagesList.path(format);

        if (formatNode.isObject()) {
            String url = formatNode.path(property).asText();
            if (url.isEmpty()) {
                log.info("The property '{}' is not found in format '{}'", property, format);
            }
            return url;
        } else {
            log.info("The format '{}' is not present in the images list or is not an object", format);
            return "";
        }
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
           log.info("The jikanList is not an array or doesn't exist: {}", jikanList);
        }

        return genericList;
    }

}