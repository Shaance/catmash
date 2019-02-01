package com.shaance.catmashinterview.dao;

import com.shaance.catmashinterview.entity.CatMashRecord;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CatMashRecordDao extends ReactiveMongoRepository<CatMashRecord, CatMashRecord> {}
