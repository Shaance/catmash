package com.shaance.catmashinterview.dto;

import com.shaance.catmashinterview.entity.Cat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatMashRecordDto implements Serializable {

	private static final long serialVersionUID = 2041008877406291336L;

	private Cat winnerCat;

	private Cat looserCat;

}
