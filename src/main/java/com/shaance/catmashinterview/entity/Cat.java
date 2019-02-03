package com.shaance.catmashinterview.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.net.URI;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Cat {

	@Id
	@NonNull
	private String id;

	@NonNull
	private URI url;


}
