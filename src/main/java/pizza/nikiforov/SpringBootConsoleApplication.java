package pizza.nikiforov;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pizza.nikiforov.autotag.service.AutotagService;
import pizza.nikiforov.geotag.service.GeotaggedPhotoConverter;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class SpringBootConsoleApplication implements CommandLineRunner {
    private final AutotagService autotagService;
    private final GeotaggedPhotoConverter geotaggedPhotoConverter;

    @Override
    public void run(String... args) {
//        autotagService.start();
        geotaggedPhotoConverter.start();
    }

    public static void main(String[] args) {
        log.info("Application started.");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        log.info("Application finished.");
    }
}
