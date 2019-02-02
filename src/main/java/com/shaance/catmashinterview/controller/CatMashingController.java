package com.shaance.catmashinterview.controller;

import com.shaance.catmashinterview.dto.CatMashRecordDto;
import com.shaance.catmashinterview.entity.Cat;
import com.shaance.catmashinterview.service.CatMashingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/cat")
public class CatMashingController {

	private CatMashingService catMashingService;

	@Autowired
	public CatMashingController(CatMashingService catMashingService) {
		this.catMashingService = catMashingService;
	}

	@GetMapping("pair")
	public Flux<Cat> getPairOfCats(){
		return catMashingService.getPairOfCats();
	}

	@PostMapping(value = "mash", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<CatMashRecordDto> saveCatMashRecord(@RequestBody CatMashRecordDto catMashRecordDto){
		return catMashingService.saveCatMashRecord(catMashRecordDto)
				.onErrorResume(e -> {
					log.error("Error while saving mash record.");
					return Mono.error(e);
				});
	}


}
