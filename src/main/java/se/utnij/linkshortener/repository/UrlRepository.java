package se.utnij.linkshortener.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;
import se.utnij.linkshortener.entity.Url;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class UrlRepository {

    private final HashOperations<String, String, Url> urlHashOperations;

    public static final String URL_TABLE = "url";
    public static final String URL_HISTORY_TABLE = "url-history";


    public Optional<Url> findUrlObjectByShortPath(String shortPath) {
        return Optional.ofNullable(urlHashOperations.get(URL_TABLE, shortPath));
    }

    public void save(Url url) {
        saveUrl(url);
        setExpiryDate(url);
        saveHistoricalUrl(url);
    }

    private void setExpiryDate(Url url) {
        if (url.getExpiredDate() != null) {
            urlHashOperations.getOperations().expire(url.getShortPath(),
                    Duration.between(Instant.now(), url.getExpiredDate().toInstant()));
        } else {
            urlHashOperations.getOperations().expire(url.getShortPath(), 7, TimeUnit.DAYS);
        }
    }

    private void saveUrl(Url url) {
        urlHashOperations.put(URL_TABLE, url.getShortPath(), url);
    }

    private void saveHistoricalUrl(Url url) {
        urlHashOperations.put(URL_HISTORY_TABLE, url.getId().toString(), url);
    }
}