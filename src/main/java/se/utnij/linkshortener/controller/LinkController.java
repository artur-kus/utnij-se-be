package se.utnij.linkshortener.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.utnij.linkshortener.service.UrlService;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class LinkController {

    private UrlService urlService;

    @GetMapping("/link/short")
    public ResponseEntity<?> shortenLink(HttpServletRequest request, @RequestParam String url) {
        return new ResponseEntity<>(urlService.shortenLink(request, url), HttpStatus.CREATED);
    }

    @GetMapping("/{shortPath}")
    public ResponseEntity<?> redirect(HttpServletRequest request, @PathVariable String shortPath) {
        return new ResponseEntity<>(urlService.redirect(request, shortPath), HttpStatus.OK);
    }
}