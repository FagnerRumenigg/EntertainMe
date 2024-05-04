package entertain_me.app.service;

import entertain_me.app.dto.jikan_api.JikanRequestAllRecord;
import entertain_me.app.dto.jikan_api.JikanResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;

@Service
public class JikanAPIService {

    private final RestTemplate restTemplate;

    public JikanAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<JikanRequestAllRecord> requestAllAnimes(Integer page) throws Exception {

        String apiUrl = String.format("https://api.jikan.moe/v4/anime?page=%d", page);

        ResponseEntity<JikanResponse> responseEntity = restTemplate.getForEntity(apiUrl, JikanResponse.class);
        List<JikanRequestAllRecord> jikanRequestAllRecords;

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JikanResponse jikanResponse = responseEntity.getBody();
            jikanRequestAllRecords = jikanResponse != null ? jikanResponse.data() : Collections.emptyList();
        }else{
            throw new Exception("The connection with the jikan-api is down. Try again later.");
        }

        return jikanRequestAllRecords;
    }
}