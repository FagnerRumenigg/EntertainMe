package entertain_me.app.service;

import entertain_me.app.dto.anime.ThemeDto;
import entertain_me.app.model.Theme;
import entertain_me.app.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ThemeService {

    @Autowired
    ThemeRepository themeRepository;

    public Theme findOrCreateTheme(String name) {
        return themeRepository.findByName(name)
                .orElseGet(() -> themeRepository.save(new Theme(name)));
    }

    public List<ThemeDto> findThemeNameByAnimeIds(List<Long> animeIds){
        return themeRepository.findDistinctNameByAnimes_IdIn(animeIds);
    }

    public List<ThemeDto> findAllTheme(){
        return themeRepository.findAllTheme();
    }

    public Set<Theme> findAllThemesById(List<Long> ids){
        List<Theme> themes = themeRepository.findAllById(ids);
        return new HashSet<>(themes);
    }
}
