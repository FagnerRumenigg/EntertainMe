package entertain_me.app.service;

import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.dto.PaginationRequestDto;
import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.model.*;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.AllAnimeInfoVoUnique;
import entertain_me.app.vo.AnimeVo;
import entertain_me.app.vo.RecommendationListVo;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
public class RecommendationService {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

    @Autowired
    private JikanService jikanService;
    @Value("${anime.favorites}")
    private String favoriteAnimeIdsString;
    private List<Long> favoriteAnimeIds;

    public Page<AllAnimeInfoVo> getByDemographic(PaginationRequestDto paginationRequestDto){
        Long userId = TokenServiceConfig.getUserIdFromContext();
        PreferencesDto preferencesDto = buildUserPreferences(userId);

        return animeService.getAnimeByDemographic(preferencesDto.demographics(),
                createPageable(paginationRequestDto));
    }

    public Page<AllAnimeInfoVo> getByGenre(PaginationRequestDto paginationRequestDto){
        Long userId = TokenServiceConfig.getUserIdFromContext();
        PreferencesDto preferencesDto = buildUserPreferences(userId);

        return animeService.getAnimeByGenre(preferencesDto.genres(),
                createPageable(paginationRequestDto));
    }

    public Page<AllAnimeInfoVo> getByStudio(PaginationRequestDto paginationRequestDto){
        Long userId = TokenServiceConfig.getUserIdFromContext();
        PreferencesDto preferencesDto = buildUserPreferences(userId);

        return animeService.getAnimeByStudio(preferencesDto.studios(),
                createPageable(paginationRequestDto));
    }

    public Page<AllAnimeInfoVo> getByTheme(PaginationRequestDto paginationRequestDto){
        Long userId = TokenServiceConfig.getUserIdFromContext();
        PreferencesDto preferencesDto = buildUserPreferences(userId);

        return animeService.getAnimeByTheme(preferencesDto.themes(),
                createPageable(paginationRequestDto));
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

    public Page<AllAnimeInfoVo> buildUpsideDownAnime(PaginationRequestDto paginationRequestDto){
        List<AllAnimeInfoVo> upsideDownListAnime = new ArrayList<>();
        Long userId = TokenServiceConfig.getUserIdFromContext();
        PreferencesDto preferencesDto = buildUserPreferences(userId);

        List<AllAnimeInfoVo> animesByDemographic = animeService.getAnimeByOtherDemographic(preferencesDto.demographics(),  createPageable(paginationRequestDto));

        List<AllAnimeInfoVo> animesByGenre = animeService.getAnimeByOtherGenre(preferencesDto.genres(),  createPageable(paginationRequestDto));

        List<AllAnimeInfoVo> animesByStudio = animeService.getAnimeByOtherStudio(preferencesDto.studios(),  createPageable(paginationRequestDto));

        List<AllAnimeInfoVo> animesByTheme = animeService.getAnimeByOtherTheme(preferencesDto.themes(),  createPageable(paginationRequestDto));

        upsideDownListAnime.addAll(animesByDemographic);
        upsideDownListAnime.addAll(animesByGenre);
        upsideDownListAnime.addAll(animesByStudio);
        upsideDownListAnime.addAll(animesByTheme);

        long totalElements = animesByDemographic.size()
                + animesByGenre.size()
                + animesByStudio.size()
                + animesByTheme.size();
        return  new PageImpl<>(upsideDownListAnime, createPageable
                (paginationRequestDto), totalElements);
    }

    public Page<AllAnimeInfoVo> getMyAnimeListTopAnimes(PaginationRequestDto paginationRequestDto) throws Exception {
        return jikanService.getTopAnimesJikan(createPageable(paginationRequestDto));
    }

    public Page<AllAnimeInfoVo> getFavoriteEntertainMeTeam(PaginationRequestDto paginationRequestDto){
        return animeService.getEntertainMeTeamFavoriteAnimes(favoriteAnimeIds ,createPageable(paginationRequestDto));
    }

    private Pageable createPageable(PaginationRequestDto paginationRequestDto) {
        return PageRequest.of(paginationRequestDto.page(), paginationRequestDto.size());
    }
    @PostConstruct
    public void init() {
        favoriteAnimeIds = Arrays.stream(favoriteAnimeIdsString.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
