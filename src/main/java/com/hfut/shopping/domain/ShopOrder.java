package com.hfut.shopping.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hfut.shopping.LFU.LFU;
import com.hfut.shopping.LFU.impl.LFUImpl;
import com.hfut.shopping.mapper.ShopMapper;

import lombok.Data;

@Data
@Component
public class ShopOrder {
	
	
	LFU<Integer, Shop> cache=LFUImpl.getLFU();

	List<OrderUtil> orderUtil;
	
	Shop shop;
	
	public int getPrice(ShopMapper mapper) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		int id=shop.getId();
		Shop shop2 = cache.get(id);
		if(shop2==null) {
			shop =mapper.select(shop);
			cache.add(id, shop);
		}else {
			shop=shop2;
		}
		int a=0,b = 0,c=0;
		for (OrderUtil util : orderUtil) {
			switch(util.getType()) {
			case 1:
				a=util.getPrice();
				break;
			case  2:
				b=util.getPrice();
				break;
			case  3:
				c=util.getPrice();
			}
		}
		return shop.getPay().getPay(a, b, c);
	}
}

@Data
class OrderUtil{
	int type;
	int price;
}
