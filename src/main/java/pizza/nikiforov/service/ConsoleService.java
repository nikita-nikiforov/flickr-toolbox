package pizza.nikiforov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class ConsoleService {
    private final FlickrService flickrService;
    private BufferedReader reader;
    private String flickrKey;
    private String flickrSecret;

    public void start() throws IOException {
//        getFlickrKey();
//        getFlickrSecret();
        flickrService.initFlickr();
    }

    @PostConstruct
    public void initReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void getFlickrKey() throws IOException {
        System.out.println("Enter Flickr API key: ");
        flickrKey = reader.readLine();
    }

    private void getFlickrSecret() throws IOException {
        System.out.println("Enter Flickr API secret: ");
        flickrKey = reader.readLine();
    }
}
