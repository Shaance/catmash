package com.shaance.catmashinterview.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Document
public class CatMashRecord {

	private String catId1;
	private String catId2;
	private String winnerCatId;
	private LocalDateTime localDateTime;

}
