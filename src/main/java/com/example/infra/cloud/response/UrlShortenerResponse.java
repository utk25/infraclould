package com.example.infra.cloud.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Builder
@Getter
@ToString
public class UrlShortenerResponse {

	private final String shortenedUrl;
	private final String originalUrl;
	private Map<String, Integer> stats;
}
