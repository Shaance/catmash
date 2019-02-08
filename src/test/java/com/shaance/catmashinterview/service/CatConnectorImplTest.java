package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.connector.CatConnectorImpl;
import com.shaance.catmashinterview.dto.CatDataDto;
import com.shaance.catmashinterview.dto.CatDto;
import com.shaance.catmashinterview.entity.Cat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CatConnectorImplTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private CatConnectorImpl catConnector;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = NullPointerException.class)
	public void getCatsFromNullURI() {
		catConnector.getCatsFromStringURI(null);
	}

	@Test
	public void getCatsFromURI() throws URISyntaxException {
		URI validUri = new URI("http://latelier.co/data/cats.json");
		CatDataDto catDataDto = new CatDataDto();
		catDataDto.setImages(Arrays.asList(
				new CatDto("id1", "url1"),
				new CatDto("id2", "url2"))
		);
		Mockito.when(restTemplate.getForEntity(validUri, CatDataDto.class))
				.thenReturn(ResponseEntity.ok(catDataDto));
		Stream<Cat> result = catConnector.getCatsFromStringURI(validUri.toString());
		assertTrue(result.count() > 0);
	}

	@Test
	public void getCatsFromBadURI() throws URISyntaxException {
		URI invalidUri = new URI("http://latelier.co/data/cats.json");
		Mockito.when(restTemplate.getForEntity(invalidUri, CatDataDto.class))
				.thenThrow(HttpClientErrorException.class);
		Stream<Cat> result = catConnector.getCatsFromStringURI(invalidUri.toString());
		assertEquals(0, result.count());
	}

}