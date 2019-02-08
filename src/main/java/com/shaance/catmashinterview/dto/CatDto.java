package com.shaance.catmashinterview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatDto implements Serializable {

	private static final long serialVersionUID = -3978560335658370148L;
	private String id;
	private String url;

}
