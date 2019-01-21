package com.hfut.shopping.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SimpleShop {

	private Integer id;

	protected String name;

	private String address;

	private String phone;

	private String introduce;

	private String avatar;

	private Integer isShopping;
	
	private Productor productor;
	
	private List<Commodity> commoditys;
	
	public SimpleShop() {
		productor=new Productor();
	}
}
