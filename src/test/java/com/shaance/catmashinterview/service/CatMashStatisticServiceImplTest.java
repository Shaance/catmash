package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dao.CatDao;
import com.shaance.catmashinterview.dao.CatMashRecordDao;
import com.shaance.catmashinterview.entity.Cat;
import com.shaance.catmashinterview.entity.CatMashRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.util.Pair;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

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
	public void getAllTimeMostVoted() throws URISyntaxException {

		String allTimeMostVotedCat = "id2";

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

		//it should use "id2" to find the cat, if not it will produce NPE
		Mockito.when(catDao.findById("id2")).thenReturn(Mono.just(new Cat(allTimeMostVotedCat, new URI(""))));

		Pair<Cat, Long> result = catMashStatisticService.getAllTimeMostVoted();
		Assert.assertEquals(4, result.getSecond(), 0);
		Assert.assertEquals("id2", result.getFirst().getId());

	}

	@Test
	public void getTodayMostVoted() throws URISyntaxException {
		String todayMostVotedCat = "id2";
		String allTimeMostVotedCat = "id1";

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

		//it should use "id2" to find the cat, if not it will produce NPE
		Mockito.when(catDao.findById("id2")).thenReturn(Mono.just(new Cat(todayMostVotedCat, new URI(""))));

		Pair<Cat, Long> result = catMashStatisticService.getTodayMostVoted();
		Assert.assertEquals(3, result.getSecond(), 0);
		Assert.assertEquals("id2", result.getFirst().getId());

	}


}