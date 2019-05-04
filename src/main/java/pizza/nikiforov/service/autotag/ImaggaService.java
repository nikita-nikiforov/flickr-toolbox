package pizza.nikiforov.service.autotag;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pizza.nikiforov.config.ImaggaProperties;
import pizza.nikiforov.exception.ImaggaProcessException;
import pizza.nikiforov.model.TagsResponse;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ImaggaService {
    private final RestTemplate restTemplate;
    private final ImaggaProperties imaggaProperties;

    private String tagsEndpoint = "https://api.imagga.com/v2/tags";

    public TagsResponse getImaggaTagsResponse(String imageUrl) {
        String requestUrl = UriComponentsBuilder
                .fromUriString(tagsEndpoint)
                .queryParam("image_url", imageUrl)
                .queryParam("language", "en,uk,ru")
                .build().toUriString();
        HttpEntity<String> entity = new HttpEntity<>("parameters", createHeaders());
        try {
            return restTemplate.exchange(requestUrl, HttpMethod.GET, entity, TagsResponse.class).getBody();
        } catch (HttpClientErrorException e) {
            throw new ImaggaProcessException("Cannot send request to Imagga for image colors retrieving.", e);
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String auth = imaggaProperties.getApiKey() + ":" + imaggaProperties.getApiSecret();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
