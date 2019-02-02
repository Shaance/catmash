package com.shaance.catmashinterview.dao;

import com.shaance.catmashinterview.entity.CatMashRecord;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatMashRecordDao extends ReactiveMongoRepository<CatMashRecord, String> {}
