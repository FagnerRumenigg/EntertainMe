package entertain_me.app.service;

import entertain_me.app.dto.anime.DemographicDto;
import entertain_me.app.dto.anime.GenreDto;
import entertain_me.app.dto.anime.StudioDto;
import entertain_me.app.dto.anime.ThemeDto;
import entertain_me.app.model.Anime;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.AnimeVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import entertain_me.app.repository.anime.AnimeRepository;

import java.util.*;
import java.util.function.Function;
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

    public Page<AllAnimeInfoVo> getAnimeByTitle(String title) {

        if (title == null || title.trim().isEmpty()) {
            log.warn("Title is empty");
            throw new IllegalArgumentException("Title is empty");
        }
        Pageable pageable = PageRequest.of(1, 5);

        Page<AnimeVo> animeList = animeRepository.findAllAnimeInfoByTitle(title.trim(), pageable);
        return buildAllAnimeInfoVoList(animeList);
    }

    private Page<AllAnimeInfoVo> buildAllAnimeInfoVoList(Page<AnimeVo> animeList) {
        List<Long> animeIds = animeList.stream().map(AnimeVo::id).collect(Collectors.toList());

        List<DemographicDto> demographics = demographicService.findDemographicNameByAnimeIds(animeIds);
        List<StudioDto> studios = studioService.findStudioNameByAnimeIds(animeIds);
        List<GenreDto> genres = genreService.findGenreNameByAnimeIds(animeIds);
        List<ThemeDto> themes = themeService.findThemeNameByAnimeIds(animeIds);

        Map<Long, List<String>> demographicsMap = groupByIdAndName(demographics, DemographicDto::demographicId, DemographicDto::name);
        Map<Long, List<String>> studiosMap = groupByIdAndName(studios, StudioDto::studioId, StudioDto::name);
        Map<Long, List<String>> genresMap = groupByIdAndName(genres, GenreDto::genreId, GenreDto::name);
        Map<Long, List<String>> themesMap = groupByIdAndName(themes, ThemeDto::themeId, ThemeDto::name);

         List<AllAnimeInfoVo> allAnimeInfoVo = animeList.stream().map(anime -> {
            Long animeId = anime.id();

            List<String> demographicsNames = Optional.ofNullable(demographicsMap.get(animeId))
                    .orElse(Collections.emptyList());

            List<String> studiosNames = Optional.ofNullable(studiosMap.get(animeId))
                    .orElse(Collections.emptyList());

            List<String> genresNames = Optional.ofNullable(genresMap.get(animeId))
                    .orElse(Collections.emptyList());

            List<String> themesNames = Optional.ofNullable(themesMap.get(animeId))
                    .orElse(Collections.emptyList());

            return new AllAnimeInfoVo(
                    anime.title(),
                    anime.source(),
                    anime.status(),
                    anime.ageRating(),
                    anime.synopsys(),
                    anime.episodes(),
                    anime.year(),
                    demographicsNames,
                    studiosNames,
                    genresNames,
                    themesNames
            );
        }).toList();
        int totalElements = allAnimeInfoVo.size();
        int start = (int) animeList.getPageable().getOffset();
        int end = Math.min((start + animeList.getPageable().getPageSize()), totalElements);

        if (start >= totalElements) {
            start = 0;
            end = Math.min(animeList.getPageable().getPageSize(), totalElements);
        }

        List<AllAnimeInfoVo> pagedAnimeList = allAnimeInfoVo.subList(start, end);

        return new PageImpl<>(pagedAnimeList, animeList.getPageable(), totalElements);
    }

    private List<AllAnimeInfoVo> buildAllAnimeInfoVoList(List<AnimeVo> animeList) {
        List<Long> animeIds = animeList.stream().map(AnimeVo::id).collect(Collectors.toList());

        List<DemographicDto> demographics = demographicService.findDemographicNameByAnimeIds(animeIds);
        List<StudioDto> studios = studioService.findStudioNameByAnimeIds(animeIds);
        List<GenreDto> genres = genreService.findGenreNameByAnimeIds(animeIds);
        List<ThemeDto> themes = themeService.findThemeNameByAnimeIds(animeIds);

        Map<Long, List<String>> demographicsMap = groupByIdAndName(demographics, DemographicDto::demographicId, DemographicDto::name);
        Map<Long, List<String>> studiosMap = groupByIdAndName(studios, StudioDto::studioId, StudioDto::name);
        Map<Long, List<String>> genresMap = groupByIdAndName(genres, GenreDto::genreId, GenreDto::name);
        Map<Long, List<String>> themesMap = groupByIdAndName(themes, ThemeDto::themeId, ThemeDto::name);

        return animeList.stream().map(anime -> {
            Long animeId = anime.id();

            List<String> demographicsNames = Optional.ofNullable(demographicsMap.get(animeId))
                    .orElse(Collections.emptyList());

            List<String> studiosNames = Optional.ofNullable(studiosMap.get(animeId))
                    .orElse(Collections.emptyList());

            List<String> genresNames = Optional.ofNullable(genresMap.get(animeId))
                    .orElse(Collections.emptyList());

            List<String> themesNames = Optional.ofNullable(themesMap.get(animeId))
                    .orElse(Collections.emptyList());

            return new AllAnimeInfoVo(
                    anime.title(),
                    anime.source(),
                    anime.status(),
                    anime.ageRating(),
                    anime.synopsys(),
                    anime.episodes(),
                    anime.year(),
                    demographicsNames,
                    studiosNames,
                    genresNames,
                    themesNames
            );
        }).toList();
    }

    private <T> Map<Long, List<String>> groupByIdAndName(List<T> dtoList,
                                                         Function<T, Long> idExtractor,
                                                         Function<T, String> nameExtractor) {
        return dtoList.stream()
                .collect(Collectors.groupingBy(idExtractor,
                        Collectors.mapping(nameExtractor, Collectors.toList())));
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

    public Page<AllAnimeInfoVo> getAnimeByDemographic(List<Long> demographicIds, Pageable pageable) {
        return buildAllAnimeInfoVoList(animeRepository.findAnimeByDemographic(demographicIds, pageable));
    }

    public Page<AllAnimeInfoVo> getAnimeByGenre(List<Long> genreIds, Pageable pageable) {
        return buildAllAnimeInfoVoList(animeRepository.findAnimeByGenre(genreIds, pageable));
    }

    public Page<AllAnimeInfoVo> getAnimeByStudio(List<Long> studioIds, Pageable pageable) {
        return buildAllAnimeInfoVoList(animeRepository.findAnimeByStudio(studioIds, pageable));
    }

    public List<AllAnimeInfoVo> getAnimeByOtherTheme(List<Long> themeIds, Pageable pageable) {
        return buildAllAnimeInfoVoList(animeRepository.findAnimeByOtherTheme(themeIds, pageable));
    }

    public List<AllAnimeInfoVo> getAnimeByOtherDemographic(List<Long> demographicIds, Pageable pageable) {
        return buildAllAnimeInfoVoList(animeRepository.findAnimeByOtherDemographic(demographicIds, pageable));
    }

    public List<AllAnimeInfoVo> getAnimeByOtherGenre(List<Long> genreIds, Pageable pageable) {
        return buildAllAnimeInfoVoList(animeRepository.findAnimeByOtherGenre(genreIds, pageable));
    }

    public List<AllAnimeInfoVo> getAnimeByOtherStudio(List<Long> studioIds, Pageable pageable) {
        return buildAllAnimeInfoVoList(animeRepository.findAnimeByOtherStudio(studioIds, pageable));
    }

    public Page<AllAnimeInfoVo> getAnimeByTheme(List<Long> themeIds, Pageable pageable) {
        return buildAllAnimeInfoVoList(animeRepository.findAnimeByTheme(themeIds, pageable));
    }

    public Page<AllAnimeInfoVo> getEntertainMeTeamFavoriteAnimes(List<Long> favoriteAnimes, Pageable pageable) {
        log.info(pageable.getPageNumber());
        log.info(animeRepository.findEntertainMeTeamFavoriteAnimes(favoriteAnimes, pageable).getContent());
        return buildAllAnimeInfoVoList(animeRepository.findEntertainMeTeamFavoriteAnimes(favoriteAnimes, pageable));
    }
}