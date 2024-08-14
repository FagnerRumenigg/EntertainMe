package entertain_me.app.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Component
public class ScheduledTasks {

    @Autowired
    JikanService jikanService;

    @Scheduled(cron = "00 55 21 * * TUE")
    public void updateDatabase() throws Exception {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        log.info("Starting database update task at the following time: " + LocalDateTime.now().format(format));
        jikanService.getAllAnimesJikan();
        log.info("Task finished at: "+LocalDateTime.now().format(format));
    }


}
