package se.utnij.linkshortener.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.utnij.linkshortener.entity.Url;
import se.utnij.linkshortener.repository.UrlRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

    @Value("${site.url}")
    private String SITE_URL;

    private final UrlRepository urlRepository;


    public Response shortenLink(HttpServletRequest request, String longEncodedUrl) {
        String decodedUrl = decodeUrl(longEncodedUrl);
        Url url = new Url(getClientIp(request), decodedUrl);
        urlRepository.save(url);
        return new Response(SITE_URL + url.getShortPath());
    }

    public Response redirect(HttpServletRequest request, String shortPath) {
        Optional<Url> urlOptional = urlRepository.findUrlObjectByShortPath(shortPath);
        if (urlOptional.isEmpty()) return new Response(SITE_URL);
        Url url = urlOptional.get();
        url.increaseCountOfVisit();
        url.setLastVisitIpAddress(getClientIp(request));
        urlRepository.save(url);
        return new Response(url.getUrl());
    }

    public String decodeUrl(String encodedUrl) {
        return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8);
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        return (xForwardedForHeader == null)
                ? request.getRemoteAddr()
                : xForwardedForHeader.split(",")[0].trim();
    }

    public record Response(String url) {
    }
}