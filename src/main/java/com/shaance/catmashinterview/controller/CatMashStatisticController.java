package com.shaance.catmashinterview.controller;

import com.shaance.catmashinterview.dto.CatWithNumberOfVotesDto;
import com.shaance.catmashinterview.dto.CatWithWinningRatioDto;
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

	@GetMapping("votes/all")
	public Flux<CatWithNumberOfVotesDto> getCatsWithAllTimeVotesInDescOrder(){
		return catMashStatisticService.getCatsWithAllTimeVotesInDescOrder();
	}

	@GetMapping("votes/today")
	public Flux<CatWithNumberOfVotesDto> getCatsWithTodayVotesInDescOrder(){
		return catMashStatisticService.getCatsWithTodayVotesInDescOrder();
	}

	@GetMapping("ratio/all")
	public Flux<CatWithWinningRatioDto> getCatsWithAllTimeWinningRatioInDescOrder(){
		return catMashStatisticService.getCatsWithAllTimeWinningRatioInDescOrder();
	}

	@GetMapping("ratio/today")
	public Flux<CatWithWinningRatioDto> getCatsWithTodayWinningRatioInDescOrder(){
		return catMashStatisticService.getCatsWithTodayWinningRatioInDescOrder();
	}

}
