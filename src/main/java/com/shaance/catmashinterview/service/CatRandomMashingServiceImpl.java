package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.entity.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Random matching service, naive way of returning pair of cats
 */
@Component
public class CatRandomMashingServiceImpl implements CatMashingService {

	private CatDataService catDataService;

	@Autowired
	public CatRandomMashingServiceImpl(CatDataService catDataService) {
		this.catDataService = catDataService;
	}

	@Override
	public Flux<Cat> getPairOfCats() {
		return catDataService.getCats()
				.parallel()
				.sorted(randomComparator())
				.take(2);
	}

	private static <T> Comparator<T> randomComparator() {
		Map<Object, UUID> randomIds = new IdentityHashMap<>();
		Map<Object, Integer> uniqueIds = new IdentityHashMap<>();
		return Comparator.comparing((T e) -> randomIds.computeIfAbsent(e, k -> UUID.randomUUID()))
				.thenComparing(e -> uniqueIds.computeIfAbsent(e, k -> uniqueIds.size()));
	}

}
