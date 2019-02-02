package com.shaance.catmashinterview.controller;

import com.shaance.catmashinterview.entity.Cat;
import com.shaance.catmashinterview.service.CatMashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/cat")
public class CatMatchingController {

	private CatMashingService catMashingService;

	@Autowired
	public CatMatchingController(CatMashingService catMashingService) {
		this.catMashingService = catMashingService;
	}

	@GetMapping("pair")
	public Flux<Cat> getPairOfCats(){
		return catMashingService.getPairOfCats();
	}


}
