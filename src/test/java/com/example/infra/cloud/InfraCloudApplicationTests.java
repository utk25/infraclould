package com.example.infra.cloud;

import com.example.infra.cloud.controller.UrlShortener;
import com.example.infra.cloud.request.UrlShortenerRequest;
import com.example.infra.cloud.response.UrlShortenerResponse;
import com.example.infra.cloud.service.UrlShortenerService;
import com.example.infra.cloud.service.impl.DefaultUrlShortenerStrategy;
import com.example.infra.cloud.service.impl.UrlShortenerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class InfraCloudApplicationTests {

	private UrlShortener urlShortener;

	@BeforeEach
	void setup() {
		DefaultUrlShortenerStrategy defaultUrlShortenerStrategy = new DefaultUrlShortenerStrategy(7,
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "http://localhost:8080/");
		UrlShortenerService urlShortenerService = new UrlShortenerServiceImpl(defaultUrlShortenerStrategy);
		urlShortener = new UrlShortener(urlShortenerService);
	}

	@Test
	void testShortenUrlUsingDefaultStrategy() {
		UrlShortenerRequest urlShortenerRequest = UrlShortenerRequest.builder().longUrl("https://www.google.com/").build();
		ResponseEntity<UrlShortenerResponse> response = urlShortener.shortenUrl(urlShortenerRequest);
		Assertions.assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
		Assertions.assertNotNull(response.getBody());
		String shortLink = response.getBody().getShortenedUrl();
		response = urlShortener.shortenUrl(urlShortenerRequest);
		Assertions.assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(shortLink, response.getBody().getShortenedUrl());
	}
}
