package entertain_me.app.service;

import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.dto.PaginationRequestDto;
import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.model.*;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.JikanSeasonNowVo;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private AnimeUserInteractionService animeUserInteractionService;

    @Value("${anime.favorites:#{null}}")
    private String favoriteAnimeIdsString;
    private List<Long> favoriteAnimeIds;

    public Page<AllAnimeInfoVo> getByDemographic(Integer page, Integer size){
        Long userId = TokenServiceConfig.getUserIdFromContext();
        PreferencesDto preferencesDto = buildUserPreferences(userId);

        return animeService.getAnimeByDemographic(preferencesDto.demographics(),
                createPageable(new PaginationRequestDto(page, size)));
    }

    public Page<AllAnimeInfoVo> getByGenre(Integer page, Integer size){
        Long userId = TokenServiceConfig.getUserIdFromContext();
        PreferencesDto preferencesDto = buildUserPreferences(userId);

        return animeService.getAnimeByGenre(preferencesDto.genres(),
                createPageable(new PaginationRequestDto(page, size)));
    }

    public Page<AllAnimeInfoVo> getByStudio(Integer page, Integer size){
        Long userId = TokenServiceConfig.getUserIdFromContext();
        PreferencesDto preferencesDto = buildUserPreferences(userId);

        return animeService.getAnimeByStudio(preferencesDto.studios(),
                createPageable(new PaginationRequestDto(page, size)));
    }

    public Page<AllAnimeInfoVo> getByTheme(Integer page, Integer size){

        Long userId = TokenServiceConfig.getUserIdFromContext();
        PreferencesDto preferencesDto = buildUserPreferences(userId);

        return animeService.getAnimeByTheme(preferencesDto.themes(),
                createPageable(new PaginationRequestDto(page, size)));
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

    public Page<AllAnimeInfoVo> buildUpsideDownAnime(Integer page, Integer size){
        PaginationRequestDto paginationRequestDto = new PaginationRequestDto(page, size);

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

    public Page<AllAnimeInfoVo> getMyAnimeListTopAnimes(Integer page, Integer size) throws Exception {
        return jikanService.getTopAnimesJikan(createPageable(new PaginationRequestDto(page, size)));
    }

    public Page<JikanSeasonNowVo> getSeasonNowJikan(Integer page, Integer size) throws Exception {
        return jikanService.getSeasonNowJikan(createPageable(new PaginationRequestDto(page, size)), size);
    }

    public Page<AllAnimeInfoVo> getFavoriteEntertainMeTeam(Integer page, Integer size){
        if(favoriteAnimeIds != null){
            return animeService.getEntertainMeTeamFavoriteAnimes(favoriteAnimeIds ,createPageable(new PaginationRequestDto(page, size)));
        }
        return Page.empty();
    }

    private Pageable createPageable(PaginationRequestDto paginationRequestDto) {
        return PageRequest.of(paginationRequestDto.page(), paginationRequestDto.size());
    }

    public void getRecommendationMasterBlasterTop(){

    }

    @PostConstruct
    public void init() {
        if(favoriteAnimeIdsString != null && !favoriteAnimeIdsString.isEmpty()){
            favoriteAnimeIds = Arrays.stream(favoriteAnimeIdsString.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }else {
            log.info("variable anime.favorites not found or is empty");
            favoriteAnimeIds = null;
        }
    }

    public void testTranslate() throws Exception {
        TranslationService translationService = new TranslationService();
        String response = translationService.post();
        log.info(TranslationService.prettify(response));
    }
}
