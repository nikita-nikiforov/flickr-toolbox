package pizza.nikiforov;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pizza.nikiforov.service.ConsoleService;

@SpringBootApplication
@Log4j2
public class SpringBootConsoleApplication implements CommandLineRunner {
    @Autowired
    private ConsoleService consoleService;

    @Override
    public void run(String... args) throws Exception {
        consoleService.start();
    }

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        log.info("APPLICATION FINISHED");
    }
}
