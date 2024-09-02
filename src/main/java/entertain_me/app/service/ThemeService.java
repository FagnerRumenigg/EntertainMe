package entertain_me.app.service;

import entertain_me.app.dto.anime.StudioDto;
import entertain_me.app.dto.anime.ThemeDto;
import entertain_me.app.model.Studio;
import entertain_me.app.model.Theme;
import entertain_me.app.repository.StudioRepository;
import entertain_me.app.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ThemeService {

    @Autowired
    ThemeRepository themeRepository;

    public Theme findOrCreateTheme(String name) {
        return themeRepository.findByName(name)
                .orElseGet(() -> themeRepository.save(new Theme(name)));
    }

    public List<ThemeDto> findStudioNameByAnimeIds(List<Long> animeIds){
        return themeRepository.findDistinctNameByAnimes_IdIn(animeIds);
    }
}
