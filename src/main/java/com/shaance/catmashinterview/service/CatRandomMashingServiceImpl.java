package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dao.CatDao;
import com.shaance.catmashinterview.dao.CatMashRecordDao;
import com.shaance.catmashinterview.dto.CatMashRecordDto;
import com.shaance.catmashinterview.entity.Cat;
import com.shaance.catmashinterview.entity.CatMashRecord;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Random matching service, naive way of returning pair of cats
 */

@Slf4j
@Service
public class CatRandomMashingServiceImpl implements CatMashingService {

	private CatDataService catDataService;
	private CatMashRecordDao catMashRecordDao;
	private CatDao catDao;
	private ModelMapper modelMapper;

	@Autowired
	public CatRandomMashingServiceImpl(CatDataService catDataService, CatMashRecordDao catMashRecordDao,
	                                   CatDao catDao, ModelMapper modelMapper) {

		this.catDataService = catDataService;
		this.catMashRecordDao = catMashRecordDao;
		this.catDao = catDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public Flux<Cat> getPairOfCats() {
		return catDataService.getCats()
				.parallel()
				.sorted(randomComparator())
				.take(2);
	}

	@Override
	public Mono<CatMashRecordDto> saveCatMashRecord(@NonNull CatMashRecordDto catMashRecordDto) {

		if (StringUtils.isEmpty(catMashRecordDto.getWinnerCatId())  || StringUtils.isEmpty(catMashRecordDto.getLoserCatId())){
			return Mono.error(new IllegalArgumentException("catMashRecordDto has null or empty field(s)."));
		}

		Mono<Boolean> winnerCatExists = catDao.findById(catMashRecordDto.getWinnerCatId())
				.hasElement();

		Mono<Boolean> loserCatExists = catDao.findById(catMashRecordDto.getLoserCatId())
				.hasElement();

		return winnerCatExists.concatWith(loserCatExists)
				.all(exists -> exists)
				.flatMap(exists -> {
					if(exists){
						CatMashRecord catMashRecord = modelMapper.map(catMashRecordDto, CatMashRecord.class);
						catMashRecord.setLocalDateTime(LocalDateTime.now());
						return catMashRecordDao.save(catMashRecord)
								.map(catMashRecord1 -> modelMapper.map(catMashRecord1, CatMashRecordDto.class))
								.onErrorResume(e -> {
									log.error("Error while saving cat mash record.", e);
									return Mono.error(e);
								});
					} else {
						return Mono.error(new IllegalArgumentException("One or more catId does not exist in database."));
					}
				});

	}

	private static <T> Comparator<T> randomComparator() {
		Map<Object, UUID> randomIds = new IdentityHashMap<>();
		Map<Object, Integer> uniqueIds = new IdentityHashMap<>();
		return Comparator.comparing((T e) -> randomIds.computeIfAbsent(e, k -> UUID.randomUUID()))
				.thenComparing(e -> uniqueIds.computeIfAbsent(e, k -> uniqueIds.size()));
	}

}
