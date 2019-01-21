package com.hfut.shopping.mapper;

import org.springframework.stereotype.Component;

import com.hfut.shopping.domain.Commodity;

@Component
public interface CommodityMapper {
	
	Commodity selectCommodity(Integer id);

}
