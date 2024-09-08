package com.example.infra.cloud.service.impl;

import com.example.infra.cloud.request.UrlShortenerRequest;
import com.example.infra.cloud.response.UrlShortenerResponse;
import com.example.infra.cloud.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

	private final Map<String, String> longToShortMap;
	private final Map<String, String> shortToLongMap;
	private final int size;
	private final String randomString;
	private final String prefix;
	private final Random random;

	@Autowired
	public UrlShortenerServiceImpl(@Value("${shorten.url.size}") int size, @Value("${random.string}") String randomString,
								   @Value("${shorten.url.prefix}") String prefix) {
		this.longToShortMap = new HashMap<>();
		this.shortToLongMap = new HashMap<>();
		this.size = size;
		this.randomString = randomString;
		this.prefix = prefix;
		random = new Random();
	}

	@Override
	public UrlShortenerResponse shortenUrl(UrlShortenerRequest urlShortenerRequest) {
		if (longToShortMap.containsKey(urlShortenerRequest.getLongUrl())) {
			return UrlShortenerResponse.builder().shortenedUrl(longToShortMap.get(urlShortenerRequest.getLongUrl())).build();
		}
		char[] result = new char[size];
		while (true) {
			for (int i = 0; i < size; i++) {
				int randomIndex = random.nextInt(randomString.length() - 1);
				result[i] = randomString.charAt(randomIndex);
			}
			String shortLink = new String(result);
			String newShortUrl = prefix + shortLink;
			if (!shortToLongMap.containsKey(newShortUrl)) {
				longToShortMap.put(urlShortenerRequest.getLongUrl(), newShortUrl);
				shortToLongMap.put(newShortUrl, urlShortenerRequest.getLongUrl());
				return UrlShortenerResponse.builder().shortenedUrl(newShortUrl).build();
			}
		}
	}
}
