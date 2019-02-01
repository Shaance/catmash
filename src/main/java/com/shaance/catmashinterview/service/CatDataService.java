package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.entity.Cat;
import reactor.core.publisher.Flux;

public interface CatDataService {

	/**
	 * @return flux of cats
	 */
	Flux<Cat> getCats();
}
