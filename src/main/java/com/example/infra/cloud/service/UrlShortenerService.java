package com.example.infra.cloud.service;

import com.example.infra.cloud.request.UrlShortenerRequest;
import com.example.infra.cloud.response.UrlShortenerResponse;
import org.springframework.stereotype.Service;

@Service
public interface UrlShortenerService {

	UrlShortenerResponse shortenUrl(UrlShortenerRequest urlShortenerRequest);

	UrlShortenerResponse getOriginalUrl(String shortUrl);

	UrlShortenerResponse getStats();
}
