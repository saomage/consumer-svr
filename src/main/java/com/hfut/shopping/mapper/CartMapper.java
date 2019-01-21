package com.hfut.shopping.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hfut.shopping.domain.CartMsg;
import com.hfut.shopping.domain.ShopOrder;

@Component
public interface CartMapper {

	List<CartMsg> selectCartMsg(CartMsg msg);
	
	int insertCartMsg(CartMsg cart);
	
	int delete(Integer id);
	
	List<ShopOrder> selectOrder(Long consumerId);
}
