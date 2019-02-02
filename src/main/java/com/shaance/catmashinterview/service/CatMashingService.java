package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dto.CatMashRecordDto;
import com.shaance.catmashinterview.entity.Cat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CatMashingService {


	/**
	 * @return flux (pair) of cats for mashing
	 */
	Flux<Cat> getPairOfCats();


	/**
	 * @param catMashRecordDto the mash record to be saved
	 * @return the mash record that has been saved
	 */
	Mono<CatMashRecordDto> saveCatMashRecord(CatMashRecordDto catMashRecordDto);

}
