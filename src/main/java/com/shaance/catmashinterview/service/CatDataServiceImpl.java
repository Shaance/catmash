package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.connector.CatConnector;
import com.shaance.catmashinterview.dao.CatDao;
import com.shaance.catmashinterview.entity.Cat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CatDataServiceImpl implements CatDataService {

	@Value("${atelier.cats.uri}")
	private String stringUrl;

	private CatConnector catConnector;
	private CatDao catDao;

	@Autowired
	public CatDataServiceImpl(CatConnector catConnector, CatDao catDao) {
		this.catConnector = catConnector;
		this.catDao = catDao;
	}

	@Override
	public Flux<Cat> getCats() {
		return catDao.findAll()
				.switchIfEmpty(catDao.saveAll(Flux.fromStream(catConnector.getCatsFromStringURI(stringUrl))))
				.onErrorResume(e -> {
					log.error("Error while retrieving cats.", e);
					return Mono.error(e);
				});
	}
}
