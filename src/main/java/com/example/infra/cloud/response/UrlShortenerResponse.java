package com.example.infra.cloud.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UrlShortenerResponse {

	private final String shortenedUrl;
}
