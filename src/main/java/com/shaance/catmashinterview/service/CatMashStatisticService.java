package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.entity.Cat;
import org.springframework.data.util.Pair;

public interface CatMashStatisticService {

	/**
	 * @return the all time most voted Cat with its associated number of votes
	 */
	Pair<Cat, Long> getAllTimeMostVoted();

	/**
	 * @return today's most voted cat with its associated number of votes
	 */
	Pair<Cat, Long> getTodayMostVoted();

}
