package com.example.infra.cloud.service.impl;

import com.example.infra.cloud.service.UrlShortenerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.Collectors;

@Service("defaultUrlShortenerStrategy")
public class DefaultUrlShortenerStrategy implements UrlShortenerStrategy {

	private final Map<String, String> longToShortMap;
	private final Map<String, String> shortToLongMap;
	private final int size;
	private final String randomString;
	private final String prefix;
	private final Random random;
	private final Map<String, Integer> doaminsMap;
	private final int topDomainSize;

	@Autowired
	public DefaultUrlShortenerStrategy(@Value("${shorten.url.size}") int size, @Value("${random.string}") String randomString,
									   @Value("${shorten.url.prefix}") String prefix, @Value("${top.domain.size}") int topDomainSize) {
		this.topDomainSize = topDomainSize;
		this.doaminsMap = new HashMap<>();
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
		String domain = getDomain(longUrl);
		doaminsMap.put(domain, doaminsMap.getOrDefault(domain, 0) + 1);
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
		String prefixedUrl = prefix + shortUrl;
		return shortToLongMap.getOrDefault(prefixedUrl, "");
	}

	@Override
	public Map<String, Integer> getStats() {
		List<Pair> list = computeTopKDomains();
		return list.stream().collect(Collectors.toMap(Pair::domain, Pair::count));
	}

	private List<Pair> computeTopKDomains() {
		PriorityQueue<String> topDomains = new PriorityQueue<>((o1, o2) -> doaminsMap.get(o1) - doaminsMap.get(o2));
		for(Map.Entry<String, Integer> entry : doaminsMap.entrySet()) {
			topDomains.add(entry.getKey());
			if (topDomains.size() > topDomainSize) {
				topDomains.remove();
			}
		}
		List<Pair> list = new ArrayList<>(topDomainSize);
		for (String s : topDomains) {
			list.add(new Pair(s, doaminsMap.getOrDefault(s, 0)));
		}
		return list;
	}

	record Pair(String domain, int count) {}
}
