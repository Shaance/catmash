package com.shaance.catmashinterview.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CatDataDto implements Serializable {

	private static final long serialVersionUID = -5176689324929894392L;
	private List<CatDto> images;
}
