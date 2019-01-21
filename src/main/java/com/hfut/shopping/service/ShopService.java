package com.hfut.shopping.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hfut.shopping.domain.Shop;
import com.hfut.shopping.domain.SimpleShop;

@Component
public interface ShopService {

	List<Shop> selectAll(Shop shop);

	Shop select(Shop shop);

	List<SimpleShop> simpleSelectAll(SimpleShop shop);
	
	SimpleShop simpleSelect(SimpleShop shop);
}
