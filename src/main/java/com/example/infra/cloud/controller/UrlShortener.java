package com.example.infra.cloud.controller;

import com.example.infra.cloud.request.UrlShortenerRequest;
import com.example.infra.cloud.response.UrlShortenerResponse;
import com.example.infra.cloud.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
