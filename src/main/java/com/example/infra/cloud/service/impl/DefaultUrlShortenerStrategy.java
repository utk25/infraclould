package com.example.infra.cloud.service.impl;

import com.example.infra.cloud.service.UrlShortenerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service("defaultUrlShortenerStrategy")
public class DefaultUrlShortenerStrategy implements UrlShortenerStrategy {

	private final Map<String, String> longToShortMap;
	private final Map<String, String> shortToLongMap;
	private final int size;
	private final String randomString;
	private final String prefix;
	private final Random random;

	@Autowired
	public DefaultUrlShortenerStrategy(@Value("${shorten.url.size}") int size, @Value("${random.string}") String randomString,
									   @Value("${shorten.url.prefix}") String prefix) {
		this.longToShortMap = new HashMap<>();
		this.shortToLongMap = new HashMap<>();
		this.size = size;
		this.randomString = randomString;
		this.prefix = prefix;
		random = new Random();
	}

	@Override
	public String shortenUrl(String longUrl) {
		if (longToShortMap.containsKey(longUrl)) {
			return longToShortMap.get(longUrl);
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
				longToShortMap.put(longUrl, newShortUrl);
				shortToLongMap.put(newShortUrl, longUrl);
				return newShortUrl;
			}
		}
	}

	@Override
	public String getOriginalUrl(String shortUrl) {
		return "";
	}
}
