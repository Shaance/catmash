package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dto.CatMashRecordDto;
import com.shaance.catmashinterview.entity.Cat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.URISyntaxException;

public class CatRandomMashingServiceImplTest {

	@Mock
	private CatDataService catDataService;

	@InjectMocks
	private CatRandomMashingServiceImpl catRandomMashingService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getPairOfCats() throws URISyntaxException {
		Mockito.when(catDataService.getCats())
				.thenReturn(Flux.just(
						new Cat("id1", new URI("uri1")),
						new Cat("id2", new URI("uri2")),
						new Cat("id3", new URI("uri3")),
						new Cat("id4", new URI("uri4")),
						new Cat("id5", new URI("uri5")),
						new Cat("id6", new URI("uri6"))
				));
		Assert.assertEquals(2L, (long) catRandomMashingService.getPairOfCats().count().block());
	}

	@Test(expected = NullPointerException.class)
	public void saveNullCatMashRecord() {
		catRandomMashingService.saveCatMashRecord(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveCatMashRecordWhenWinnerCatIsNull() {
		catRandomMashingService.saveCatMashRecord(new CatMashRecordDto(null, null)).block();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveCatMashRecordWhenWinnerCatIdNull() {
		catRandomMashingService.saveCatMashRecord(
				new CatMashRecordDto(null, ""))
				.block();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveCatMashRecordWhenLooserCatIsNull() {
		catRandomMashingService.saveCatMashRecord(
				new CatMashRecordDto("", null))
				.block();
	}


}