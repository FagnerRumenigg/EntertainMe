package entertain_me.app.component;

import entertain_me.app.dto.anime.JikanAnimeIdsDto;
import entertain_me.app.service.AnimeService;
import entertain_me.app.service.JikanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
@Component
public class ScheduledTasks {

    @Autowired
    JikanService jikanService;

    @Autowired
    AnimeService animeService;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(cron = "00 05 23 * * FRI")
    public void updateAnimeDatabase() throws Exception {
        log.info("Starting updateAnimeDatabase task at the following time: {}",  LocalDateTime.now().format(format));
        jikanService.getAllAnimesJikan();
        log.info("Task updateAnimeDatabase finished at: "+LocalDateTime.now().format(format));
        updateAnimeStreaming();
    }

    public void updateAnimeStreaming() throws Exception {
        log.info("Starting updateAnimeStreaming task at the following time: {}",LocalDateTime.now().format(format));
        List<JikanAnimeIdsDto> jikanAnimeId = animeService.getAllJikanId();

        jikanService.setAnimeStreaming(jikanAnimeId);
        log.info("Task updateAnimeStreaming finished at: "+LocalDateTime.now().format(format));
    }

    @Scheduled(cron = "01 00 00 * * *")
    public void compactLogMidnight() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        log.info("Compacting log by time, at: {}",LocalDateTime.now().format(format));
    }
}
