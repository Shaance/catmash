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

		Cat allTimeMostVotedCat = new Cat("id2", new URI("uri1"));

		Flux<CatMashRecord> mockFlux =
				Flux.just(
						new CatMashRecord("", new Cat("id1", new URI("uri1")), new Cat("id2", new URI("uri2")), LocalDateTime.now()),
						new CatMashRecord("", new Cat("id1", new URI("uri1")), new Cat("id2", new URI("uri2")), LocalDateTime.now()),
						new CatMashRecord("", allTimeMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now()),
						new CatMashRecord("", allTimeMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now()),
						new CatMashRecord("", new Cat("id3", new URI("uri1")), new Cat("id2", new URI("uri2")), LocalDateTime.now()),
						new CatMashRecord("", allTimeMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now()),
						new CatMashRecord("", allTimeMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now())
				);

		Mockito.when(catMashRecordDao.findAll()).thenReturn(mockFlux);

		//it should use "id2" to find the cat, if not it will produce NPE
		Mockito.when(catDao.findById("id2")).thenReturn(Mono.just(allTimeMostVotedCat));

		Pair<Cat, Long> result = catMashStatisticService.getAllTimeMostVoted();
		Assert.assertEquals(4, result.getSecond(), 0);
		Assert.assertEquals("id2", result.getFirst().getId());

	}

	@Test
	public void getTodayMostVoted() throws URISyntaxException {
		Cat todayMostVotedCat = new Cat("id2", new URI("uri1"));
		Cat allTimeMostVotedCat = new Cat("id1", new URI("uri1"));

		Flux<CatMashRecord> mockFlux =
				Flux.just(
						new CatMashRecord("", allTimeMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", allTimeMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", allTimeMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", allTimeMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", allTimeMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", allTimeMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now().minusDays(1)),
						new CatMashRecord("", todayMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now()),
						new CatMashRecord("", todayMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now()),
						new CatMashRecord("", new Cat("id3", new URI("uri1")), new Cat("id2", new URI("uri2")), LocalDateTime.now()),
						new CatMashRecord("", todayMostVotedCat, new Cat("id2", new URI("uri2")), LocalDateTime.now())
				);

		Mockito.when(catMashRecordDao.findAll()).thenReturn(mockFlux);

		//it should use "id2" to find the cat, if not it will produce NPE
		Mockito.when(catDao.findById("id2")).thenReturn(Mono.just(todayMostVotedCat));

		Pair<Cat, Long> result = catMashStatisticService.getTodayMostVoted();
		Assert.assertEquals(3, result.getSecond(), 0);
		Assert.assertEquals("id2", result.getFirst().getId());

	}


}