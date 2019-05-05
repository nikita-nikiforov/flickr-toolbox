package pizza.nikiforov.geotag.service;

import com.flickr4java.flickr.photos.GeoData;
import com.flickr4java.flickr.photos.Photo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pizza.nikiforov.flickr.service.PhotoService;
import pizza.nikiforov.flickr.service.UserService;
import pizza.nikiforov.geotag.model.FeatureWrapper;
import pizza.nikiforov.geotag.model.GeojsonFeature;
import pizza.nikiforov.geotag.model.GeojsonFlickrProperties;
import pizza.nikiforov.geotag.model.GeojsonGeometry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
@RequiredArgsConstructor
public class GeotaggedPhotoConverter {
    private final UserService userService;
    private final PhotoService photoService;

    public void start() {
        String userNsid = userService.getNsid();
        Set<String> extras = Stream.of("geo", "url_m").collect(Collectors.toSet());
        List<Photo> userPublicPhotos = photoService.getUserPublicPhotos(userNsid, extras);
        FeatureWrapper geojson = convertPhotosToGeojson(userPublicPhotos);
        saveAsFile(geojson);
    }

    private FeatureWrapper convertPhotosToGeojson(List<Photo> photos) {
        FeatureWrapper featureWrapper = new FeatureWrapper("FeatureCollection", new ArrayList<>());
        photos.stream()
                .filter(photo -> photo.getGeoData() != null)
                .map(photo -> {
                    GeoData geoData = photo.getGeoData();
                    // Create "geometry"
                    GeojsonGeometry geojsonGeometry = new GeojsonGeometry();
                    geojsonGeometry.setType("Point");
                    geojsonGeometry.setCoordinates(Arrays.asList(geoData.getLongitude(), geoData.getLatitude()));
                    // Create "properties"
                    GeojsonFlickrProperties properties = new GeojsonFlickrProperties(photo.getUrl(), photo.getTitle(),
                            photo.getMediumUrl());

                    return new GeojsonFeature(geojsonGeometry, "Feature", properties);
                })
                .collect(Collectors.toCollection(featureWrapper::getFeatures));
        return featureWrapper;
    }

    private void saveAsFile(FeatureWrapper featureWrapper) {
        String json = new Gson().toJson(featureWrapper);
        File file = new File("geophotos.json");
        try{
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                log.info("File {} already has been created.", file.getName());
            } else {
                log.info("File {} has been created.", file.getName());
            }
            try(FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile())){
                fileWriter.write(json);
                log.info("Data has been successfully written into file {}.", file.getName());
            }
        } catch (IOException e) {
            log.error("Exception occurred during file creation: {}", e);
        }
    }
}