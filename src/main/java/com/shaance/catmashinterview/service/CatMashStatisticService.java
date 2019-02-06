package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dto.CatWithNumberOfVotesDto;
import reactor.core.publisher.Flux;

public interface CatMashStatisticService {

	/**
	 * @return a flux of all time most voted Cats with their associated number of votes
	 */
	Flux<CatWithNumberOfVotesDto> getAllTimeCatsWithVotes();

	/**
	 * @return a flux of today's most voted cats with their associated number of votes
	 */
	Flux<CatWithNumberOfVotesDto> getTodayCatsWithVotes();

}
