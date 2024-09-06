package entertain_me.app.service;

import entertain_me.app.dto.anime.DemographicDto;
import entertain_me.app.dto.anime.GenreDto;
import entertain_me.app.dto.anime.StudioDto;
import entertain_me.app.dto.anime.ThemeDto;
import entertain_me.app.model.Anime;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.AllAnimeInfoVoUnique;
import entertain_me.app.vo.AnimeVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entertain_me.app.repository.anime.AnimeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AnimeService {

    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    GenreService genreService;

    @Autowired
    DemographicService demographicService;

    @Autowired
    StudioService studioService;

    @Autowired
    ThemeService themeService;

    public List<AllAnimeInfoVo> getAnimeByTitle(String title) {

        if (title == null || title.trim().isEmpty()) {
            log.warn("Title is empty");
            throw new IllegalArgumentException("Title is empty");
        }

        List<Anime> animeList = animeRepository.findAllAnimeInfoByTitle(title.trim());
        List<Long> animeIds = animeList.stream().map(Anime::getId).collect(Collectors.toList());

        List<DemographicDto> demographics = demographicService.findDemographicNameByAnimeIds(animeIds);
        List<StudioDto> studios = studioService.findStudioNameByAnimeIds(animeIds);
        List<GenreDto> genres = genreService.findGenreNameByAnimeIds(animeIds);
        List<ThemeDto> themes = themeService.findStudioNameByAnimeIds(animeIds);

        Map<Long, List<String>> demographicsMap = demographics.stream()
                .collect(Collectors.toMap(DemographicDto::demographicId,
                        d -> Collections.singletonList(d.name()),
                        (existing, replacement) -> {
                            List<String> combined = new ArrayList<>(existing);
                            combined.addAll(replacement);
                            return combined;
                        }));

        Map<Long, List<String>> studiosMap = studios.stream()
                .collect(Collectors.toMap(StudioDto::studioId,
                        s -> Collections.singletonList(s.name()),
                        (existing, replacement) -> {
                            List<String> combined = new ArrayList<>(existing);
                            combined.addAll(replacement);
                            return combined;
                        }));

        Map<Long, List<String>> genresMap = genres.stream()
                .collect(Collectors.toMap(GenreDto::genreId,
                        g -> Collections.singletonList(g.name()),
                        (existing, replacement) -> {
                            List<String> combined = new ArrayList<>(existing);
                            combined.addAll(replacement);
                            return combined;
                        }));

        Map<Long, List<String>> themeMap = themes.stream()
                .collect(Collectors.toMap(ThemeDto::themeId,
                        g -> Collections.singletonList(g.name()),
                        (existing, replacement) -> {
                            List<String> combined = new ArrayList<>(existing);
                            combined.addAll(replacement);
                            return combined;
                        }));

        return animeList.stream().map(anime -> {
            Long animeId = anime.getId();
            List<String> demographicsNames = Optional.ofNullable(demographicsMap.get(animeId))
                    .orElse(Collections.emptyList());

            List<String> studiosNames = Optional.ofNullable(studiosMap.get(animeId))
                    .orElse(Collections.emptyList());

            List<String> genresNames = Optional.ofNullable(genresMap.get(animeId))
                    .orElse(Collections.emptyList());

            List<String> themesNames = Optional.ofNullable(themeMap.get(animeId))
                    .orElse((Collections.emptyList()));

            return new AllAnimeInfoVo(
                    anime.getTitle(),
                    anime.getSource(),
                    anime.getStatus(),
                    anime.getAgeRating(),
                    anime.getSynopsys(),
                    anime.getEpisodes(),
                    anime.getYear(),
                    demographicsNames,
                    studiosNames,
                    genresNames,
                    themesNames
            );
        }).collect(Collectors.toList());
    }

    private Anime getById(Long animeId) {
        return animeRepository.getReferenceById(animeId);
    }

    public List<Anime> getById(List<Long> ids) {
        return animeRepository.findAllById(ids);
    }

    public List<Anime> getRandomAnimes(Integer limit) {
        return animeRepository.findRandomAnimes(limit);
    }

    public List<AllAnimeInfoVo> getAnimeByDemographic(List<Long> demographicIds) {
        return convertToAllAnimeInfoVo(animeRepository.findAnimeByDemographic(demographicIds));
    }

    public List<AllAnimeInfoVo> getAnimeByGenre(List<Long> genreIds) {
        return convertToAllAnimeInfoVo(animeRepository.findAnimeByGenre(genreIds));
    }

    public List<AllAnimeInfoVo> getAnimeByStudio(List<Long> studioIds) {
        return convertToAllAnimeInfoVo(animeRepository.findAnimeByStudio(studioIds));
    }

    public List<AllAnimeInfoVo> getAnimeByOtherTheme(List<Long> themeIds) {
        return convertToAllAnimeInfoVo(animeRepository.findAnimeByOtherTheme(themeIds));
    }

    public List<AllAnimeInfoVo> getAnimeByOtherDemographic(List<Long> demographicIds) {
        return convertToAllAnimeInfoVo(animeRepository.findAnimeByOtherDemographic(demographicIds));
    }

    public List<AllAnimeInfoVo> getAnimeByOtherGenre(List<Long> genreIds) {
        return convertToAllAnimeInfoVo(animeRepository.findAnimeByOtherGenre(genreIds));
    }

    public List<AllAnimeInfoVo> getAnimeByOtherStudio(List<Long> studioIds) {
        return convertToAllAnimeInfoVo(animeRepository.findAnimeByOtherStudio(studioIds));
    }

    public List<AllAnimeInfoVo> getAnimeByTheme(List<Long> themeIds) {
        return convertToAllAnimeInfoVo(animeRepository.findAnimeByTheme(themeIds));
    }

    public List<AllAnimeInfoVo> getEntertainMeTeamFavoriteAnimes(List<Long> favoriteAnimes){
        return convertToAllAnimeInfoVo(animeRepository.findEntertainMeTeamFavoriteAnimes(favoriteAnimes));
    }

    private List<String> convertStringToList(String commaSeparatedString) {
        if(commaSeparatedString != null){
            return Arrays.asList(commaSeparatedString.split("\\s*,\\s*"));
        }
        return new ArrayList<>();
    }

    public List<AllAnimeInfoVo> convertToAllAnimeInfoVo(List<AllAnimeInfoVoUnique> uniqueList) {
        List<AllAnimeInfoVo> convertedList = new ArrayList<>();

        for (AllAnimeInfoVoUnique unique : uniqueList) {
            AllAnimeInfoVo converted = new AllAnimeInfoVo(
                    unique.title(),
                    unique.source(),
                    unique.status(),
                    unique.ageRating(),
                    unique.synopsys(),
                    unique.episodes(),
                    unique.year(),
                    convertStringToList(unique.demographics()),
                    convertStringToList(unique.studios()),
                    convertStringToList(unique.genres()),
                    convertStringToList(unique.themes())
            );

            convertedList.add(converted);
        }

        return convertedList;
    }

}