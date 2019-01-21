package com.hfut.shopping.domain;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Commodity {
	
	private Integer id;
	
	private String name;
	
	private String introduce;
	
	private String avatar;
	
	private Integer type;
	
	private Integer price;
	
}
