package com.shaance.catmashinterview.dto;

import com.shaance.catmashinterview.entity.Cat;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CatWithNumberOfVotesDto implements Serializable {

	private static final long serialVersionUID = 3304810323915871823L;
	private Cat cat;
	private Long votes;

}
