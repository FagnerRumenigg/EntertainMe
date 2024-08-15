package entertain_me.app.service;

import entertain_me.app.dto.anime.DemographicDto;
import entertain_me.app.dto.anime.GenreDto;
import entertain_me.app.dto.anime.StudioDto;
import entertain_me.app.model.Anime;
import entertain_me.app.vo.AllAnimeInfoVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entertain_me.app.repository.AnimeRepository;

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

  public List<AllAnimeInfoVo> getAnimeByTitle(String title) {
    if (title == null || title.trim().isEmpty()) {
      log.warn("Title is empty");
      throw new IllegalArgumentException("Title is empty");
    }

    List<Anime> animeList = animeRepository.findAllAnimeInfoByTitle(title.trim());
    List<UUID> animeIds = animeList.stream().map(Anime::getId).collect(Collectors.toList());

    List<DemographicDto> demographics = demographicService.findDemographicNameByAnimeIds(animeIds);
    List<StudioDto> studios = studioService.findStudioNameByAnimeIds(animeIds);
    List<GenreDto> genres = genreService.findGenreNameByAnimeIds(animeIds);

    Map<UUID, List<String>> demographicsMap = demographics.stream()
            .collect(Collectors.toMap(DemographicDto::animeId,
                    d -> Collections.singletonList(d.name()),
                    (existing, replacement) -> {
                      List<String> combined = new ArrayList<>(existing);
                      combined.addAll(replacement);
                      return combined;
                    }));

    Map<UUID, List<String>> studiosMap = studios.stream()
            .collect(Collectors.toMap(StudioDto::animeId,
                    s -> Collections.singletonList(s.name()),
                    (existing, replacement) -> {
                      List<String> combined = new ArrayList<>(existing);
                      combined.addAll(replacement);
                      return combined;
                    }));

    Map<UUID, List<String>> genresMap = genres.stream()
            .collect(Collectors.toMap(GenreDto::animeId,
                    g -> Collections.singletonList(g.name()),
                    (existing, replacement) -> {
                      List<String> combined = new ArrayList<>(existing);
                      combined.addAll(replacement);
                      return combined;
                    }));

    return animeList.stream().map(anime -> {
      UUID animeId = anime.getId();
      List<String> demographicsNames = Optional.ofNullable(demographicsMap.get(animeId))
              .orElse(Collections.emptyList());
      List<String> studiosNames = Optional.ofNullable(studiosMap.get(animeId))
              .orElse(Collections.emptyList());
      List<String> genresNames = Optional.ofNullable(genresMap.get(animeId))
              .orElse(Collections.emptyList());

      return new AllAnimeInfoVo(
              anime.getTitle(),
              anime.getSource(),
              anime.getStatus(),
              anime.getSynopsys(),
              anime.getEpisodes(),
              anime.getYear(),
              demographicsNames,
              studiosNames,
              genresNames
      );
    }).collect(Collectors.toList());
  }


}