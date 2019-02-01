package com.shaance.catmashinterview.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ImageDto implements Serializable {

	private static final long serialVersionUID = -3978560335658370148L;
	private String id;
	private String url;

}
