package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dao.CatMashRecordDao;
import com.shaance.catmashinterview.dto.CatMashRecordDto;
import com.shaance.catmashinterview.entity.Cat;
import com.shaance.catmashinterview.entity.CatMashRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

public class CatRandomMashingServiceImplTest {

	@Mock
	private CatDataService catDataService;

	@Mock
	private CatMashRecordDao catMashRecordDao;

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
	public void saveCatMashRecordWhenWinnerCatIdNull() throws URISyntaxException {
		catRandomMashingService.saveCatMashRecord(
				new CatMashRecordDto(new Cat(null, new URI("uri1")), null))
				.block();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveCatMashRecordWhenLooserCatIsNull() throws URISyntaxException {
		catRandomMashingService.saveCatMashRecord(
				new CatMashRecordDto(new Cat("", new URI("uri1")), null))
				.block();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveCatMashRecordWhenLooserCatIdNull() throws URISyntaxException {
		catRandomMashingService.saveCatMashRecord(
				new CatMashRecordDto(
						new Cat("", new URI("uri1")),
						new Cat(null, new URI("uri1")))
				)
				.block();
	}

	@Test
	public void saveCatMashRecord() throws URISyntaxException {
		Mockito.when(catMashRecordDao.save(Mockito.any(CatMashRecord.class)))
				.thenReturn(Mono.just(new CatMashRecord()));

		Assert.assertNotNull(
				catRandomMashingService.saveCatMashRecord(
					new CatMashRecordDto(
							new Cat("1", new URI("uri1")),
							new Cat("2", new URI("uri2"))
					)
				).block()
		);
	}


}