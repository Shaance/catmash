package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.connector.CatConnector;
import com.shaance.catmashinterview.dao.CatDao;
import com.shaance.catmashinterview.entity.Cat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component
public class CatDataServiceImpl implements CatDataService {

	@Value("${atelier.cats.uri}")
	private String uri;

	private CatConnector catConnector;
	private CatDao catDao;

	@Autowired
	public CatDataServiceImpl(CatConnector catConnector, CatDao catDao) {
		this.catConnector = catConnector;
		this.catDao = catDao;
	}

	@Override
	public Flux<Cat> getCats() {
		URI uri;
		try {
			uri = new URI(this.uri);
		} catch (URISyntaxException e) {
			log.error("Bad URI syntax.", e);
			return Flux.empty();
		}

		return catDao.findAll()
				.switchIfEmpty(catDao.saveAll(Flux.fromStream(catConnector.getCatsFromURI(uri))));
	}
}
