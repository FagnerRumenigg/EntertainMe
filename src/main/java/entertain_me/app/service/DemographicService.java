package entertain_me.app.service;

import entertain_me.app.dto.anime.DemographicDto;
import entertain_me.app.model.Demographic;
import entertain_me.app.repository.DemographicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DemographicService {

    @Autowired
    DemographicRepository demographicRepository;

    public Demographic findOrCreateDemographic(String name) {
        return demographicRepository.findByName(name)
                .orElseGet(() -> demographicRepository.save(new Demographic(name)));
    }

    public List<DemographicDto> findDemographicNameByAnimeIds(List<Long> animeIds){
        return demographicRepository.findDistinctNameByAnimes_IdIn(animeIds);
    }

    public List<DemographicDto> findAllDemographics(){
        return demographicRepository.findAllDemographics();
    }

    public Set<Demographic> findAllDemographicsById(List<Long> ids){
        List<Demographic> demographics= demographicRepository.findAllById(ids);
        return new HashSet<>(demographics);
    }
}
