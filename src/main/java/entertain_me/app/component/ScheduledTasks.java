package entertain_me.app.component;

import entertain_me.app.service.JikanService;
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
    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(cron = "00 00 19 * * MON")
    public void updateDatabase() throws Exception {
        log.info("Starting database update task at the following time: " + LocalDateTime.now().format(format));
        jikanService.getAllAnimesJikan();
        log.info("Task finished at: "+LocalDateTime.now().format(format));
    }

    @Scheduled(cron = "01 00 00 * * *")
    public void compactLogMidnight() throws Exception {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        log.info("Compacting log by time, at: {}",LocalDateTime.now().format(format));
    }
}
