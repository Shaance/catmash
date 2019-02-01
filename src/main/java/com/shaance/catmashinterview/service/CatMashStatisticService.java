package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.entity.Cat;
import reactor.core.publisher.Mono;

public interface CatMashStatisticService {

	/**
	 * @return
	 */
	Mono<Cat> getAllTimeMostVoted();

	/**
	 * @return
	 */
	Mono<Cat> getTodayMostVoted();

	/**
	 * @return
	 */
	Mono<Cat> getThisWeekMostVoted();

	/**
	 * @return
	 */
	Mono<Cat> getNumberOfVotesByCat();
}
