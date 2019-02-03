package com.shaance.catmashinterview.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CatMashRecord {

	@Id
	private String id;

	@NonNull
	private Cat winnerCat;

	@NonNull
	private Cat looserCat;

	@NonNull
	private LocalDateTime localDateTime;

}
