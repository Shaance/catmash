package com.shaance.catmashinterview.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CatWithNumberOfVotesDto implements Serializable {

	private static final long serialVersionUID = 3304810323915871823L;
	private CatDto cat;
	private Long votes;

}
