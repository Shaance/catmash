package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dto.CatWithNumberOfVotesDto;

public interface CatMashStatisticService {

	/**
	 * @return the all time most voted Cat with its associated number of votes
	 */
	CatWithNumberOfVotesDto getAllTimeMostVoted();

	/**
	 * @return today's most voted cat with its associated number of votes
	 */
	CatWithNumberOfVotesDto getTodayMostVoted();

}
