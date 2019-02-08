package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dto.CatWithNumberOfVotesDto;
import com.shaance.catmashinterview.dto.CatWithWinningRatioDto;
import reactor.core.publisher.Flux;

public interface CatMashStatisticService {

	/**
	 * @return a flux Cats with their associated all-time number of votes sorted in descending order
	 */
	Flux<CatWithNumberOfVotesDto> getCatsWithAllTimeVotesInDescOrder();

	/**
	 * @return a flux of Cats with their associated today's number of votes sorted in descending order
	 */
	Flux<CatWithNumberOfVotesDto> getCatsWithTodayVotesInDescOrder();

	/**
	 * @return a flux of Cats with their all-time winning ratio sorted in descending order
	 */
	Flux<CatWithWinningRatioDto> getCatsWithAllTimeWinningRatioInDescOrder();

	/**
	 * @return a flux of Cats with their today's winning ratio sorted in descending order
	 */
	Flux<CatWithWinningRatioDto> getCatsWithTodayWinningRatioInDescOrder();

}
