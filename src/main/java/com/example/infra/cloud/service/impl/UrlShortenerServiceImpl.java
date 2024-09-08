package com.example.infra.cloud.service.impl;

import com.example.infra.cloud.request.UrlShortenerRequest;
import com.example.infra.cloud.response.UrlShortenerResponse;
import com.example.infra.cloud.service.UrlShortenerService;
import com.example.infra.cloud.service.UrlShortenerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

	private final UrlShortenerStrategy urlShortenerStrategy;

	@Autowired
	public UrlShortenerServiceImpl(@Qualifier("defaultUrlShortenerStrategy") UrlShortenerStrategy urlShortenerStrategy) {
		this.urlShortenerStrategy = urlShortenerStrategy;
	}

	@Override
	public UrlShortenerResponse shortenUrl(UrlShortenerRequest urlShortenerRequest) {
		String shortUrl = urlShortenerStrategy.shortenUrl(urlShortenerRequest.getLongUrl());
		return UrlShortenerResponse.builder().shortenedUrl(shortUrl).build();
	}
}
