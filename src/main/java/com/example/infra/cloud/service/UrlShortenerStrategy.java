package com.example.infra.cloud.service;

public interface UrlShortenerStrategy {

	String shortenUrl(String longUrl);
	String getOriginalUrl(String shortUrl);
}
