package com.hfut.shopping.mapper;

import org.springframework.stereotype.Component;

import com.hfut.shopping.domain.Order;

@Component
public interface OrderMapper {
	
	int insertOrder(Order order);
	
	void removeToOrder(long consumerId,int orderId);

}
