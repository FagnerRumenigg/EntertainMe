package entertain_me.app.service;

import entertain_me.app.dto.jikan_api.JikanResponseDataDto;
import entertain_me.app.dto.jikan_api.JikanResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;

@Service
public class JikanAPIService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<JikanResponseDataDto> requestAllAnimes(Integer page) throws Exception {

        String apiUrl = String.format("https://api.jikan.moe/v4/anime?page=%d", page);

        ResponseEntity<JikanResponseDto> responseEntity = restTemplate.getForEntity(apiUrl, JikanResponseDto.class);
        List<JikanResponseDataDto> jikanRequestAllRecords;

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JikanResponseDto jikanResponse = responseEntity.getBody();
            jikanRequestAllRecords = jikanResponse != null ? jikanResponse.data() : Collections.emptyList();
        }else{
            throw new Exception("The connection with the jikan-api is down. Try again later.");
        }

        return jikanRequestAllRecords;
    }
}