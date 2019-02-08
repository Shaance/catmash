package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dao.CatDao;
import com.shaance.catmashinterview.dao.CatMashRecordDao;
import com.shaance.catmashinterview.dto.CatWithNumberOfVotesDto;
import com.shaance.catmashinterview.dto.CatWithWinningRatioDto;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CatMashStatisticServiceImplTest {

	@Mock
	private CatMashRecordDao catMashRecordDao;

	@Mock
	private CatDao catDao;

	@InjectMocks
	private CatMashStatisticServiceImpl catMashStatisticService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getCatsWithAllTimeVotesInDescOrderTest() throws URISyntaxException {

		String allTimeMostVotedCat = "id2";

		Flux<Cat> catFlux = Flux.just(
				new Cat("id1", new URI("uri1")),
				new Cat("id2", new URI("uri2")),
				new Cat("id3", new URI("uri2")),
				new Cat("id4", new URI("uri2"))
		);

		Flux<CatMashRecord> mockFlux =
				Flux.just(
						new CatMashRecord("","id1", "id3", LocalDateTime.now()),
						new CatMashRecord("", "id1", "id3", LocalDateTime.now()),
						new CatMashRecord("", allTimeMostVotedCat, "id3", LocalDateTime.now()),
						new CatMashRecord("", allTimeMostVotedCat, "id3", LocalDateTime.now()),
						new CatMashRecord("", "id3", "id4", LocalDateTime.now()),
						new CatMashRecord("", allTimeMostVotedCat, "id5", LocalDateTime.now()),
						new CatMashRecord("", allTimeMostVotedCat, "id5", LocalDateTime.now())
				);

		Mockito.when(catMashRecordDao.findAll()).thenReturn(mockFlux);
		Mockito.when(catDao.findAll()).thenReturn(catFlux);

		Flux<CatWithNumberOfVotesDto> result = catMashStatisticService.getCatsWithAllTimeVotesInDescOrder();
		CatWithNumberOfVotesDto mostVotedCat = result.blockFirst();
		Long numberOfElements = result.count().block();
		Assert.assertEquals(4, numberOfElements, 0);
		Assert.assertEquals("id2", mostVotedCat.getCat().getId());
		Assert.assertEquals(4, mostVotedCat.getVotes(), 0);

	}

	@Test
	public void getCatsWithTodayVotesInDescOrderTest() throws URISyntaxException {
		String todayMostVotedCat = "id2";
		String allTimeMostVotedCat = "id1";

		Flux<Cat> catFlux = Flux.just(
				new Cat("id1", new URI("uri1")),
				new Cat("id2", new URI("uri2")),
				new Cat("id3", new URI("uri2")),
				new Cat("id4", new URI("uri2"))
		);

		Flux<CatMashRecord> mockFlux =
				Flux.just(
						new CatMashRecord("", allTimeMostVotedCat, "id5", LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", allTimeMostVotedCat, "id5", LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", allTimeMostVotedCat, "id5", LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", allTimeMostVotedCat, "id5", LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", allTimeMostVotedCat, "id5", LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", allTimeMostVotedCat, "id5", LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", todayMostVotedCat, "id5", LocalDateTime.now()),
						new CatMashRecord("", todayMostVotedCat, "id5", LocalDateTime.now()),
						new CatMashRecord("", "id3", "id5", LocalDateTime.now()),
						new CatMashRecord("", todayMostVotedCat, "id5", LocalDateTime.now())
				);

		Mockito.when(catMashRecordDao.findAll()).thenReturn(mockFlux);
		Mockito.when(catDao.findAll()).thenReturn(catFlux);

		Flux<CatWithNumberOfVotesDto> result = catMashStatisticService.getCatsWithTodayVotesInDescOrder();

		CatWithNumberOfVotesDto mostVotedCat = result.blockFirst();
		Long numberOfElements = result.count().block();
		Assert.assertEquals(4, numberOfElements, 0);
		Assert.assertEquals("id2", mostVotedCat.getCat().getId());
		Assert.assertEquals(3, mostVotedCat.getVotes(), 0);


	}

	@Test
	public void getCatsWithAllTimeWinningRatioInDescOrder() throws URISyntaxException {

		Flux<Cat> catFlux = Flux.just(
				new Cat("id1", new URI("uri1")),
				new Cat("id2", new URI("uri2")),
				new Cat("id3", new URI("uri2")),
				new Cat("id4", new URI("uri2")),
				new Cat("id5", new URI("uri2")),
				new Cat("id6", new URI("uri2"))
		);

		Flux<CatMashRecord> mockFlux =
				Flux.just(
						new CatMashRecord("","id1", "id3", LocalDateTime.now()),
						new CatMashRecord("","id1", "id3", LocalDateTime.now()),
						new CatMashRecord("", "id2", "id3", LocalDateTime.now()),
						new CatMashRecord("", "id2", "id3", LocalDateTime.now()),
						new CatMashRecord("", "id3", "id4", LocalDateTime.now()),
						new CatMashRecord("", "id2", "id5", LocalDateTime.now()),
						new CatMashRecord("", "id2", "id5", LocalDateTime.now())
				);


		Mockito.when(catMashRecordDao.findAll()).thenReturn(mockFlux);
		Mockito.when(catDao.findAll()).thenReturn(catFlux);

		Map<String, Float> assertMap = new HashMap<>();
		assertMap.put("id1", 1f);
		assertMap.put("id2", 1f);
		assertMap.put("id3", 0.2f);
		assertMap.put("id4", 0f);
		assertMap.put("id5", 0f);
		assertMap.put("id6", -1f);

		Flux<CatWithWinningRatioDto> result = catMashStatisticService.getCatsWithAllTimeWinningRatioInDescOrder();

		result.toStream()
				.forEach(catWithWinningRatioDto ->
						Assert.assertEquals(assertMap.get(catWithWinningRatioDto.getCat().getId()), catWithWinningRatioDto.getWinningRatio()));


	}

	@Test
	public void getCatsWithTodayWinningRatioInDescOrder() throws URISyntaxException {

		Flux<Cat> catFlux = Flux.just(
				new Cat("id1", new URI("uri1")),
				new Cat("id2", new URI("uri2")),
				new Cat("id3", new URI("uri2")),
				new Cat("id4", new URI("uri2")),
				new Cat("id5", new URI("uri2")),
				new Cat("id6", new URI("uri2"))
		);

		Flux<CatMashRecord> mockFlux =
				Flux.just(
						new CatMashRecord("","id1", "id3", LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", "id2", "id3", LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", "id2", "id3", LocalDateTime.now()),
						new CatMashRecord("", "id3", "id4", LocalDateTime.now()),
						new CatMashRecord("", "id2", "id5", LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", "id2", "id5", LocalDateTime.now())
				);


		Mockito.when(catMashRecordDao.findAll()).thenReturn(mockFlux);
		Mockito.when(catDao.findAll()).thenReturn(catFlux);

		Map<String, Float> assertMap = new HashMap<>();
		assertMap.put("id1", -1f);
		assertMap.put("id2", 1f);
		assertMap.put("id3", 0.5f);
		assertMap.put("id4", 0f);
		assertMap.put("id5", 0f);
		assertMap.put("id6", -1f);

		Flux<CatWithWinningRatioDto> result = catMashStatisticService.getCatsWithTodayWinningRatioInDescOrder();

		result.toStream()
				.forEach(catWithWinningRatioDto ->
						Assert.assertEquals(assertMap.get(catWithWinningRatioDto.getCat().getId()), catWithWinningRatioDto.getWinningRatio()));
	}


}