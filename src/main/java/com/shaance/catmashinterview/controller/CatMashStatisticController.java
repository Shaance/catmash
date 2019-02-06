package com.shaance.catmashinterview.controller;

import com.shaance.catmashinterview.dto.CatWithNumberOfVotesDto;
import com.shaance.catmashinterview.service.CatMashStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/cat/stats")
public class CatMashStatisticController {

	private CatMashStatisticService catMashStatisticService;

	@Autowired
	public CatMashStatisticController(CatMashStatisticService catMashStatisticService) {
		this.catMashStatisticService = catMashStatisticService;
	}

	@GetMapping("all")
	public Flux<CatWithNumberOfVotesDto> getAllTimeMostVoted(){
		return catMashStatisticService.getAllTimeCatsWithVotes();
	}

	@GetMapping("today")
	public Flux<CatWithNumberOfVotesDto> getTodayMostVoted(){
		return catMashStatisticService.getTodayCatsWithVotes();
	}

}
