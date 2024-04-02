package entertain_me.app.service;

import entertain_me.app.record.jikan_api.JikanRequestAllRecord;
import entertain_me.app.record.jikan_api.JikanResponse;
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

    public List<JikanRequestAllRecord> requestAllAnimes(String apiUrl) throws Exception {

        ResponseEntity<JikanResponse> responseEntity = restTemplate.getForEntity(apiUrl, JikanResponse.class);
        List<JikanRequestAllRecord> jikanRequestAllRecords = Collections.emptyList();

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JikanResponse jikanResponse = responseEntity.getBody();
            jikanRequestAllRecords = jikanResponse != null ? jikanResponse.data() : Collections.emptyList();
        }else{
            throw new Exception("Não foi possível fazer a requisição para a API JIKAN");
        }

        return jikanRequestAllRecords;
    }
}