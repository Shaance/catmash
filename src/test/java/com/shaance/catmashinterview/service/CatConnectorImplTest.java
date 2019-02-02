package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.connector.CatConnectorImpl;
import com.shaance.catmashinterview.entity.Cat;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

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
		Stream<Cat> result = catConnector.getCatsFromURI(new URI("http://latelier.co/data/cats.json"));
		assertTrue(result.count() > 0);
	}

	@Test
	public void getCatsFromBadURI() throws URISyntaxException {
		Stream<Cat> result = catConnector.getCatsFromURI(new URI("http://latelier.co/data/catsxx.json"));
		assertEquals(0, result.count());
	}

}