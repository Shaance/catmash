package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.dao.CatDao;
import com.shaance.catmashinterview.dao.CatMashRecordDao;
import com.shaance.catmashinterview.entity.Cat;
import com.shaance.catmashinterview.entity.CatMashRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CatMashStatisticServiceImpl implements CatMashStatisticService {

	private CatMashRecordDao catMashRecordDao;
	private CatDao catDao;

	@Autowired
	public CatMashStatisticServiceImpl(CatMashRecordDao catMashRecordDao, CatDao catDao) {
		this.catMashRecordDao = catMashRecordDao;
		this.catDao = catDao;
	}

	@Override
	public Pair<Cat, Long>  getAllTimeMostVoted() {

		return getMostVotedCatWithPredicate(catMashRecord -> true);
	}

	@Override
	public Pair<Cat, Long> getTodayMostVoted() {

		return getMostVotedCatWithPredicate(catMashRecord ->
				catMashRecord.getLocalDateTime().toLocalDate().toEpochDay() == LocalDate.now().toEpochDay());

	}


	private Pair<Cat, Long> getMostVotedCatWithPredicate(Predicate<CatMashRecord> filterCondition){
		AtomicReference<Pair<String, Long>> catIdVotesPair = getCatIdVotesPair(catMashRecordDao.findAll(), filterCondition);
		String catId = catIdVotesPair.get().getFirst();
		if(StringUtils.isEmpty(catId)){
			log.warn("No catMashingRecord!");
			return null;
		} else {
			Cat cat = catDao.findById(catId).block();
			if(cat == null){
				log.error("Could not find cat.");
				return null;
			}
			return Pair.of(cat, catIdVotesPair.get().getSecond());
		}
	}

	private AtomicReference<Pair<String, Long>> getCatIdVotesPair(Flux<CatMashRecord> catMashRecordFlux, Predicate<CatMashRecord> filterCondition){
		AtomicReference<Pair<String, Long>> catIdVotesPair = new AtomicReference<>();
		catMashRecordFlux
				.toStream()
				.filter(filterCondition)
				.map(CatMashRecord::getWinnerCatId)
				.collect(Collectors.groupingBy(String::valueOf, Collectors.counting()))
				.entrySet()
				.stream()
				.max(Comparator.comparing(Map.Entry::getValue))
				.ifPresent(stringLongEntry -> catIdVotesPair.set(Pair.of(stringLongEntry.getKey(), stringLongEntry.getValue())));

		return catIdVotesPair;
	}
}
