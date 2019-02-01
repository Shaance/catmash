package com.shaance.catmashinterview.dao;

import com.shaance.catmashinterview.entity.Cat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatDao extends ReactiveMongoRepository<Cat, String> {}
