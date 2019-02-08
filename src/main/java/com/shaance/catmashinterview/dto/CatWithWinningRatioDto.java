package com.shaance.catmashinterview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CatWithWinningRatioDto implements Serializable {

	private static final long serialVersionUID = -2473254295687680256L;

	private CatDto cat;

	/**
	 * Winning ratio of the Cat, equals -1f when N/A (is not present in CatMashRecord)
	 */
	private Float winningRatio;
}
