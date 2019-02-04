package com.shaance.catmashinterview.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CatMashRecordDto implements Serializable {

	private static final long serialVersionUID = 2041008877406291336L;

	private String winnerCatId;

	private String loserCatId;

}
