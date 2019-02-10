package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dao.CatDao;
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
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class CatRandomMashingServiceImplTest {

	@Mock
	private CatDataService catDataService;

	@Mock
	private CatDao catDao;

	@Mock
	private CatMashRecordDao catMashRecordDao;

	@Mock
	private ModelMapper modelMapper;

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

	@Test(expected = IllegalArgumentException.class)
	public void saveCatMashRecordWhenOneCatIsNotInDb() throws URISyntaxException {
		String winnerCatId = "winner";
		String loserCatId = "loser";
		Mono<Cat> empty = Mono.empty();

		Mockito.when(catDao.findById(winnerCatId)).thenReturn(Mono.just(new Cat(winnerCatId, new URI("ff"))));
		Mockito.when(catDao.findById(loserCatId)).thenReturn(empty);

		catRandomMashingService.saveCatMashRecord(new CatMashRecordDto(winnerCatId, loserCatId))
				.block();
	}

	@Test(expected = Exception.class)
	public void errorWhenSavingCatMashRecord() throws URISyntaxException {
		String winnerCatId = "winner";
		String loserCatId = "loser";

		Mockito.when(catDao.findById(winnerCatId)).thenReturn(Mono.just(new Cat(winnerCatId, new URI("ff"))));
		Mockito.when(catDao.findById(loserCatId)).thenReturn(Mono.just(new Cat(loserCatId, new URI("dd"))));
		Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenCallRealMethod();
		Mockito.when(catMashRecordDao.save(Mockito.any())).thenThrow(new RuntimeException());

		catRandomMashingService.saveCatMashRecord(new CatMashRecordDto(winnerCatId, loserCatId))
				.block();
	}

	@Test
	public void savingCatMashRecord() throws URISyntaxException {
		String winnerCatId = "winner";
		String loserCatId = "loser";
		Cat winnerCat = new Cat(winnerCatId, new URI("ff"));
		Cat loserCat = new Cat(loserCatId, new URI("dd"));
		CatMashRecord mashRecord = new CatMashRecord("", winnerCatId, loserCatId, LocalDateTime.now());
		CatMashRecordDto catMashRecordDto = new CatMashRecordDto(winnerCatId, loserCatId);

		Mockito.when(catDao.findById(winnerCatId)).thenReturn(Mono.just(winnerCat));
		Mockito.when(catDao.findById(loserCatId)).thenReturn(Mono.just(loserCat));
		Mockito.when(modelMapper.map(catMashRecordDto, CatMashRecord.class)).thenReturn(mashRecord);
		Mockito.when(modelMapper.map(mashRecord, CatMashRecordDto.class)).thenReturn(catMashRecordDto);
		Mockito.when(catMashRecordDao.save(Mockito.any())).thenReturn(Mono.just(mashRecord));

		CatMashRecordDto result = catRandomMashingService.saveCatMashRecord(catMashRecordDto)
				.block();

		Assert.assertEquals(result, catMashRecordDto);
	}


}