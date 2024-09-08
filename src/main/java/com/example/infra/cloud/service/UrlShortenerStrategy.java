package com.example.infra.cloud.service;

import java.util.Map;

public interface UrlShortenerStrategy {

	String shortenUrl(String longUrl);

	String getOriginalUrl(String shortUrl);

	Map<String, Integer> getStats();

	default String getDomain(String longUrl) {
		String[] split = longUrl.split("//");
		return split[1].split("/")[0];
	}
}
