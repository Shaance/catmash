package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.entity.Cat;
import reactor.core.publisher.Flux;

public interface CatMashingService {


	/**
	 * @return flux (pair) of cats for mashing
	 */
	Flux<Cat> getPairOfCats();

}
