package pizza.nikiforov;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class SpringBootConsoleApplication implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("Run application");
    }
}
