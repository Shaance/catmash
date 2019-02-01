package com.shaance.catmashinterview.connector;

import com.shaance.catmashinterview.entity.Cat;
import reactor.core.publisher.Flux;

import java.net.URI;

public interface CatConnector {


	/**
	 * @param uri place where we can retrieve cat
	 * @return flux of cats from the URI or null when an error occurred
	 */
	Flux<Cat> getCatsFromURI(URI uri);

}
