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
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "http://localhost:8080/", 3);
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

	@Test
	void testOriginalUrlUsingDefaultStrategy() {
		UrlShortenerRequest urlShortenerRequest = UrlShortenerRequest.builder().longUrl("https://www.google.com/").build();
		ResponseEntity<UrlShortenerResponse> response = urlShortener.shortenUrl(urlShortenerRequest);
		Assertions.assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
		Assertions.assertNotNull(response.getBody());
		String shortLink = response.getBody().getShortenedUrl();
		response = urlShortener.shortenUrl(urlShortenerRequest);
		Assertions.assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(shortLink, response.getBody().getShortenedUrl());

		response = urlShortener.getOriginalUrl(shortLink);
		Assertions.assertEquals(HttpStatus.valueOf(301), response.getStatusCode());
	}

	@Test
	void testStatsUsingDefaultStrategy() {
		UrlShortenerRequest urlShortenerRequest = UrlShortenerRequest.builder().longUrl("https://www.google.com/").build();
		UrlShortenerRequest urlShortenerRequest2 = UrlShortenerRequest.builder().longUrl("https://www.google.com/search?q=testing").build();
		UrlShortenerRequest urlShortenerRequest3 = UrlShortenerRequest.builder().longUrl("https://www.youtube.com").build();
		UrlShortenerRequest urlShortenerRequest4 = UrlShortenerRequest.builder().longUrl("https://www.youtube.com/search?q=testing").build();
		UrlShortenerRequest urlShortenerRequest5 = UrlShortenerRequest.builder().longUrl("https://www.test.com").build();
		UrlShortenerRequest urlShortenerRequest6 = UrlShortenerRequest.builder().longUrl("https://www.test.com/search?q=testing").build();
		UrlShortenerRequest urlShortenerRequest7 = UrlShortenerRequest.builder().longUrl("https://www.sample.com").build();
		ResponseEntity<UrlShortenerResponse> response = urlShortener.shortenUrl(urlShortenerRequest);
		ResponseEntity<UrlShortenerResponse> response2 = urlShortener.shortenUrl(urlShortenerRequest2);
		ResponseEntity<UrlShortenerResponse> response3 = urlShortener.shortenUrl(urlShortenerRequest3);
		ResponseEntity<UrlShortenerResponse> response4 = urlShortener.shortenUrl(urlShortenerRequest4);
		ResponseEntity<UrlShortenerResponse> response5 = urlShortener.shortenUrl(urlShortenerRequest5);
		ResponseEntity<UrlShortenerResponse> response6 = urlShortener.shortenUrl(urlShortenerRequest6);
		ResponseEntity<UrlShortenerResponse> response7 = urlShortener.shortenUrl(urlShortenerRequest7);
		ResponseEntity<UrlShortenerResponse> response8 = urlShortener.getStats();
		Assertions.assertNotNull(response8.getBody());
		Assertions.assertNotNull(response8.getBody().getStats());
		Assertions.assertEquals(3, response8.getBody().getStats().size());
		Assertions.assertEquals(2, response8.getBody().getStats().get("www.google.com"));
		Assertions.assertEquals(2, response8.getBody().getStats().get("www.youtube.com"));
		Assertions.assertEquals(2, response8.getBody().getStats().get("www.test.com"));
	}
}
