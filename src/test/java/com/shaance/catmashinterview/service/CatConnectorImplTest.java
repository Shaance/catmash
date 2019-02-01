package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.connector.CatConnectorImpl;
import com.shaance.catmashinterview.entity.Cat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.util.Pair;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CatConnectorImplTest {

	private CatConnectorImpl catConnector;

	@Before
	public void setUp() {
		catConnector = new CatConnectorImpl();
	}

	@Test(expected = NullPointerException.class)
	public void getCatsFromNullURI() throws URISyntaxException {
		catConnector.getCatsFromURI(new URI(null));
	}

	/*
	* URL could change leading to test failing.
	* Should extract the part which retrieve ResponseEntity<CatDataDto> into another component
	* and then mock it
	* */
	@Test
	public void getCatsFromURI() throws URISyntaxException {
		Flux<Cat> result = catConnector.getCatsFromURI(new URI("http://latelier.co/data/cats.json"));
		assertTrue(result.hasElements().block());
		Pair<String, String> of = Pair.of("Test", "Test2");
		Pair<String, String> of2 = Pair.of("Test2", "Test");
		System.out.println(of.equals(of2));
	}

	@Test
	public void getCatsFromBadURI() throws URISyntaxException {
		Flux<Cat> result = catConnector.getCatsFromURI(new URI("http://latelier.co/data/catsxx.json"));
		assertEquals(Flux.empty(), result);
	}

}