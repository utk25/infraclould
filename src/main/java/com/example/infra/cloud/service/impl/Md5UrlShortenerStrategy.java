package com.example.infra.cloud.service.impl;

import com.example.infra.cloud.service.UrlShortenerStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Md5UrlShortenerStrategy implements UrlShortenerStrategy {

	@Override
	public String shortenUrl(String longUrl) {
		//To be implemented
		return "";
	}

	@Override
	public String getOriginalUrl(String shortUrl) {
		//To be implemented
		return "";
	}

	@Override
	public Map<String, Integer> getStats() {
		//To be implemented
		return Map.of();
	}
}
