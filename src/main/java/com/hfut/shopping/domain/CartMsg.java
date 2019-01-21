package com.hfut.shopping.domain;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CartMsg {

	int id;
	
	int commodityId;
	
	String commodityName;
	
	int shopId;
	
	String shopName;
	
	int type;
	
	int price;
	
	Long consumerId;
}
