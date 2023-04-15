package zavrsni.erasmus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import zavrsni.erasmus.service.NatjecajService;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private NatjecajService natjecajService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleTask() {
        natjecajService.closeExpiredNatjecaji();
    }
}
