package com.hfut.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hfut.shopping.domain.Shop;
import com.hfut.shopping.domain.SimpleShop;
import com.hfut.shopping.mapper.ShopMapper;
import com.hfut.shopping.service.ShopService;

@Component
public class ShopServiceImpl implements ShopService{

	@Autowired
	ShopMapper smapper;

	@Override
	public List<Shop> selectAll(Shop shop) {
		return smapper.selectAll(shop);
	}

	@Override
	public Shop select(Shop shop) {
		return smapper.select(shop);
	}

	@Override
	public List<SimpleShop> simpleSelectAll(SimpleShop shop) {
		return smapper.simpleSelectAll(shop);
	}

	@Override
	public SimpleShop simpleSelect(SimpleShop shop) {
		return smapper.simpleSelect(shop);
	}
	
	
}
