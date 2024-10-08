package com.example.infra.cloud.controller;

import com.example.infra.cloud.request.UrlShortenerRequest;
import com.example.infra.cloud.response.UrlShortenerResponse;
import com.example.infra.cloud.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class UrlShortener {

	private final UrlShortenerService urlShortenerService;

	@Autowired
	public UrlShortener(UrlShortenerService urlShortenerService) {
		this.urlShortenerService = urlShortenerService;
	}

	@PostMapping("/shorten")
	public ResponseEntity<UrlShortenerResponse> shortenUrl(@RequestBody UrlShortenerRequest urlShortenerRequest) {
		UrlShortenerResponse urlShortenerResponse = urlShortenerService.shortenUrl(urlShortenerRequest);
		return ResponseEntity.ok(urlShortenerResponse);
	}

	@GetMapping("/{shortUrl}")
	public ResponseEntity<UrlShortenerResponse> getOriginalUrl(@PathVariable String shortUrl) {
		UrlShortenerResponse urlShortenerResponse = urlShortenerService.getOriginalUrl(shortUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(urlShortenerResponse.getOriginalUrl()));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@GetMapping("/stats")
	public ResponseEntity<UrlShortenerResponse> getStats() {
		UrlShortenerResponse urlShortenerResponse = urlShortenerService.getStats();
		return ResponseEntity.ok(urlShortenerResponse);
	}
}
