package com.shaance.catmashinterview.connector;

import com.shaance.catmashinterview.entity.Cat;

import java.net.URI;
import java.util.stream.Stream;

public interface CatConnector {


	/**
	 * @param uri place where we can retrieve cat
	 * @return flux of cats from the URI
	 */
	Stream<Cat> getCatsFromURI(URI uri);

}
