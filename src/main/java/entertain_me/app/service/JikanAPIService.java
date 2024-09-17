package entertain_me.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertain_me.app.dto.anime.JikanAnimeIdsDto;
import entertain_me.app.dto.jikan_api.JikanAnimeStreamingDto;
import entertain_me.app.dto.jikan_api.JikanResponseDataDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class JikanAPIService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper(); // Instância compartilhada


    public List<JikanResponseDataDto> requestAllAnimes(Integer page) throws Exception {
        String apiUrl = String.format("https://api.jikan.moe/v4/anime?page=%d", page);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        return buildJikanResponseDataDto(responseEntity);
    }

    public List<JikanAnimeStreamingDto> requestAnimeStreaming(List<JikanAnimeIdsDto> jikanAnimeIdsDtoList) throws Exception {
        List<JikanAnimeStreamingDto> allAnimeStreamingDtoList = new ArrayList<>();

        if (jikanAnimeIdsDtoList.isEmpty()) {
            log.info("Anime list is empty");
            return allAnimeStreamingDtoList;
        }

        for (JikanAnimeIdsDto jikanAnimeIdsDto : jikanAnimeIdsDtoList) {
            try {
                Thread.sleep(1500);

                String apiUrl = String.format("https://api.jikan.moe/v4/anime/%d/streaming", jikanAnimeIdsDto.jikanId());

                ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

                List<JikanAnimeStreamingDto> animeStreamingDtoList = buildJikanAnimeStreamingDto(responseEntity, jikanAnimeIdsDto.id());
                log.info("Anime {} processe. {}", jikanAnimeIdsDto.id(), animeStreamingDtoList);
                allAnimeStreamingDtoList.addAll(animeStreamingDtoList);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve o status de interrupção
                log.error("Request interrupted: ", e);
                throw new RuntimeException("Request interrupted", e);
            } catch (Exception e) {
                log.error("Failed to fetch streaming info for Jikan ID " + jikanAnimeIdsDto.jikanId() + ": " + e.getMessage());
            }
        }

        return allAnimeStreamingDtoList;
    }


    public List<JikanResponseDataDto> requestTopAnimes() throws Exception {
        String apiUrl = "https://api.jikan.moe/v4/top/anime";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        return buildJikanResponseDataDto(responseEntity);
    }

    private List<JikanAnimeStreamingDto> buildJikanAnimeStreamingDto(ResponseEntity<String> responseEntity, Long animeId) throws Exception {

        List<JikanAnimeStreamingDto> jikanAnimeStreamingDtoList = new ArrayList<>();

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();

            if (responseBody != null) {
                JsonNode dataReturn = mapper.readTree(responseBody);
                JsonNode dataAnimeList = dataReturn.path("data");

                if (dataAnimeList.isArray()) {
                    for (JsonNode animeStreaming : dataAnimeList) {
                        String name = animeStreaming.path("name").asText(null); // Usar valor padrão vazio
                        String url = animeStreaming.path("url").asText(null); // Usar valor padrão vazio

                        if (name != null && !name.isEmpty() && url != null && !url.isEmpty()) {
                            JikanAnimeStreamingDto animeStreamingDto = new JikanAnimeStreamingDto(animeId, name, url);
                            jikanAnimeStreamingDtoList.add(animeStreamingDto);
                        }
                    }
                }
            } else {
               log.info("Response body is null");
            }
        } else {
           log.info("Failed to fetch streaming info: " + responseEntity.getStatusCode());
        }

        return jikanAnimeStreamingDtoList;
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