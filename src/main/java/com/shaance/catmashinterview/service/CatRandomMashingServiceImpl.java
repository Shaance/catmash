package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dao.CatMashRecordDao;
import com.shaance.catmashinterview.dto.CatMashRecordDto;
import com.shaance.catmashinterview.entity.Cat;
import com.shaance.catmashinterview.entity.CatMashRecord;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
public class CatRandomMashingServiceImpl implements CatMashingService {

	private CatDataService catDataService;
	private CatMashRecordDao catMashRecordDao;

	@Autowired
	public CatRandomMashingServiceImpl(CatDataService catDataService, CatMashRecordDao catMashRecordDao) {
		this.catDataService = catDataService;
		this.catMashRecordDao = catMashRecordDao;
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
		if (catMashRecordDto.getWinnerCat() == null  || catMashRecordDto.getLooserCat() == null){
			return Mono.error(new IllegalArgumentException("catMashRecordDto has null or empty field(s)."));
		}
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		CatMashRecord catMashRecord = modelMapper.map(catMashRecordDto, CatMashRecord.class);
		catMashRecord.setLocalDateTime(LocalDateTime.now());
		return catMashRecordDao.save(catMashRecord)
				.map(catMashRecord1 -> modelMapper.map(catMashRecord1, CatMashRecordDto.class))
				.onErrorResume(e -> {
					log.error("Error while saving cat mash record.", e);
					return Mono.error(e);
				});
	}

	private static <T> Comparator<T> randomComparator() {
		Map<Object, UUID> randomIds = new IdentityHashMap<>();
		Map<Object, Integer> uniqueIds = new IdentityHashMap<>();
		return Comparator.comparing((T e) -> randomIds.computeIfAbsent(e, k -> UUID.randomUUID()))
				.thenComparing(e -> uniqueIds.computeIfAbsent(e, k -> uniqueIds.size()));
	}

}
