package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dao.CatDao;
import com.shaance.catmashinterview.dao.CatMashRecordDao;
import com.shaance.catmashinterview.dto.CatDto;
import com.shaance.catmashinterview.dto.CatWithNumberOfVotesDto;
import com.shaance.catmashinterview.entity.CatMashRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CatMashStatisticServiceImpl implements CatMashStatisticService {

	private CatMashRecordDao catMashRecordDao;
	private CatDao catDao;

	@Autowired
	public CatMashStatisticServiceImpl(CatMashRecordDao catMashRecordDao, CatDao catDao) {
		this.catMashRecordDao = catMashRecordDao;
		this.catDao = catDao;
	}

	@Override
	public Flux<CatWithNumberOfVotesDto> getAllTimeCatsWithVotes() {

		return getMostVotedCatsWithPredicate(catMashRecord -> true);
	}

	@Override
	public Flux<CatWithNumberOfVotesDto> getTodayCatsWithVotes() {
		return getMostVotedCatsWithPredicate(catMashRecord ->
				catMashRecord.getLocalDateTime().toLocalDate().toEpochDay() == LocalDate.now().toEpochDay());
	}

	private Flux<CatWithNumberOfVotesDto> getMostVotedCatsWithPredicate(Predicate<CatMashRecord> filterCondition){
		Map<String, Long> catIdVotesMap = getCatIdVotesMap(catMashRecordDao.findAll(), filterCondition);

		return catDao.findAll()
				.map(cat -> new CatWithNumberOfVotesDto(new CatDto(cat.getId(), cat.getUrl().toString()), catIdVotesMap.getOrDefault(cat.getId(), 0L)))
				.sort(Comparator.comparing(CatWithNumberOfVotesDto::getVotes).reversed());
	}


	private Map<String, Long> getCatIdVotesMap(Flux<CatMashRecord> catMashRecordFlux, Predicate<CatMashRecord> filterCondition){
		return catMashRecordFlux
				.toStream()
				.filter(filterCondition)
				.map(CatMashRecord::getWinnerCatId)
				.collect(Collectors.groupingBy(String::valueOf, Collectors.counting()));

	}
}
