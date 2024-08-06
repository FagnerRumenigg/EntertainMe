package entertain_me.app.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Log4j2
@Component
public class ScheduledTasks {

    @Autowired
    JikanService jikanService;

    @Scheduled(cron = "0 0 4 * * MON")
    public void updateDatabase() throws Exception {
        log.info("Starting database update task at the following time: " + LocalDateTime.now());
        jikanService.getAllAnimesJikan();
    }

}
