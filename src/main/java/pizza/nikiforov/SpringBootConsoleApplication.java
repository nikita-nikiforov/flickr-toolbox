package pizza.nikiforov;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pizza.nikiforov.autotag.service.AutotagService;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class SpringBootConsoleApplication implements CommandLineRunner {
    private final AutotagService autotagService;

    @Override
    public void run(String... args) {
        autotagService.start();
    }

    public static void main(String[] args) {
        log.info("Application started.");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        log.info("Application finished.");
    }
}
