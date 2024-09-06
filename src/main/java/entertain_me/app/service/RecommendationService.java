package entertain_me.app.service;

import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.model.*;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.AllAnimeInfoVoUnique;
import entertain_me.app.vo.AnimeVo;
import entertain_me.app.vo.RecommendationListVo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RecommendationService {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

    @Value("${anime.favorites}")
    private String favoriteAnimeIdsString;
    private List<Long> favoriteAnimeIds;
    public RecommendationListVo getHomeListByUser() {

        Long userId = TokenServiceConfig.getUserIdFromContext();

        PreferencesDto preferencesDto = buildUserPreferences(userId);

        List<AnimeVo> animesByDemographic = animeService.getAnimeByDemographic(preferencesDto.demographics());
        List<AnimeVo> animesByGenre = animeService.getAnimeByGenre(preferencesDto.genres());
        List<AnimeVo> animesByStudio = animeService.getAnimeByStudio(preferencesDto.studios());
        List<AnimeVo> animesByTheme = animeService.getAnimeByTheme(preferencesDto.themes());

        return new RecommendationListVo(
                animesByDemographic,
                animesByGenre,
                animesByStudio,
                animesByTheme,
                buildFavoriteAnimes(animeService.getFavoriteWorkerAnime(favoriteAnimeIds)),
                null,
                buildUpsideDownAnime(preferencesDto)
        );
    }

    private PreferencesDto buildUserPreferences(Long userId) {
        Set<Object> preferences = userService.getUserPreferences(userId);

        List<Long> demographicsIds = new ArrayList<>();
        List<Long> genreIds = new ArrayList<>();
        List<Long> studioIds = new ArrayList<>();
        List<Long> themeIds = new ArrayList<>();

        for (Object preference : preferences) {
            if (preference instanceof Demographic) {
                demographicsIds.add(((Demographic) preference).getIdDemographic());
            } else if (preference instanceof Genre) {
                genreIds.add(((Genre) preference).getIdGenre());
            } else if (preference instanceof Studio) {
                studioIds.add(((Studio) preference).getIdStudio());
            } else if (preference instanceof Theme) {
                themeIds.add(((Theme) preference).getIdTheme());
            }
        }

        return new PreferencesDto(
                demographicsIds,
                genreIds,
                studioIds,
                themeIds
        );
    }

    private List<AllAnimeInfoVo> buildFavoriteAnimes(List<AllAnimeInfoVoUnique> allAnimeInfoVoUnique) {
        Map<String, AllAnimeInfoVo> animeMap = new HashMap<>();

        for (AllAnimeInfoVoUnique animeInfoVoUnique : allAnimeInfoVoUnique) {
            String title = animeInfoVoUnique.title();
            AllAnimeInfoVo existingAnimeInfoVo = animeMap.get(title);

            if (existingAnimeInfoVo == null) {
                existingAnimeInfoVo = new AllAnimeInfoVo(
                        animeInfoVoUnique.title(),
                        animeInfoVoUnique.source(),
                        animeInfoVoUnique.status(),
                        animeInfoVoUnique.ageRating(),
                        animeInfoVoUnique.synopsys(),
                        animeInfoVoUnique.episodes(),
                        animeInfoVoUnique.year(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>()
                );
                animeMap.put(title, existingAnimeInfoVo);
            }

            existingAnimeInfoVo = new AllAnimeInfoVo(
                    existingAnimeInfoVo.title(),
                    existingAnimeInfoVo.source(),
                    existingAnimeInfoVo.status(),
                    existingAnimeInfoVo.ageRating(),
                    existingAnimeInfoVo.synopsys(),
                    existingAnimeInfoVo.episodes(),
                    existingAnimeInfoVo.year(),
                    addUniqueElements(existingAnimeInfoVo.demographics(), animeInfoVoUnique.demographics()),
                    addUniqueElements(existingAnimeInfoVo.studios(), animeInfoVoUnique.studios()),
                    addUniqueElements(existingAnimeInfoVo.genres(), animeInfoVoUnique.genres()),
                    addUniqueElements(existingAnimeInfoVo.themes(), animeInfoVoUnique.themes())
            );

            animeMap.put(title, existingAnimeInfoVo);
        }

        return new ArrayList<>(animeMap.values());
    }

    private List<AnimeVo> buildUpsideDownAnime(PreferencesDto preferencesDto){
        List<AnimeVo> upsideDownListAnime = new ArrayList<>();

        List<AnimeVo> animesByDemographic = animeService.getAnimeByOtherDemographic(preferencesDto.demographics());
        List<AnimeVo> animesByGenre = animeService.getAnimeByOtherGenre(preferencesDto.genres());
        List<AnimeVo> animesByStudio = animeService.getAnimeByOtherStudio(preferencesDto.studios());
        List<AnimeVo> animesByTheme = animeService.getAnimeByOtherTheme(preferencesDto.themes());

        upsideDownListAnime.addAll(animesByDemographic);
        upsideDownListAnime.addAll(animesByGenre);
        upsideDownListAnime.addAll(animesByStudio);
        upsideDownListAnime.addAll(animesByTheme);

        return upsideDownListAnime;
    }

    private List<String> addUniqueElements(List<String> existingList, String newElements) {
        if (existingList == null) {
            existingList = new ArrayList<>();
        }

        if (!existingList.contains(newElements)) {
            existingList.add(newElements);
        }

        return existingList;
    }


    @PostConstruct
    public void init() {
        System.out.println(favoriteAnimeIdsString);
        favoriteAnimeIds = Arrays.stream(favoriteAnimeIdsString.split(","))
                .map(String::trim)  // Remove espaços em branco antes de converter
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
