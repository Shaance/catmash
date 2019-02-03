package com.shaance.catmashinterview.controller;

import com.shaance.catmashinterview.entity.Cat;
import com.shaance.catmashinterview.service.CatMashStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cat/stats")
public class CatMashStatisticController {

	private CatMashStatisticService catMashStatisticService;

	@Autowired
	public CatMashStatisticController(CatMashStatisticService catMashStatisticService) {
		this.catMashStatisticService = catMashStatisticService;
	}

	@GetMapping("all")
	public Pair<Cat,Long> getAllTimeMostVoted(){
		return catMashStatisticService.getAllTimeMostVoted();
	}

	@GetMapping("today")
	public Pair<Cat,Long> getTodayMostVoted(){
		return catMashStatisticService.getTodayMostVoted();
	}

}
