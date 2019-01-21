package com.hfut.shopping;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hfut.shopping.domain.Shop;
import com.hfut.shopping.domain.SimpleShop;
import com.hfut.shopping.mapper.ShopMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerSvrApplicationTests {

	@Autowired
	ShopMapper mapper;

	@Autowired
	SimpleShop simpleShop;
	
	Shop shop;

	@Before
	public void before() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
	}

	@Test
	public void contextLoads() throws Exception {
		Shop shop2 = new Shop();
		shop2.setId(2);
		shop=new Shop("gw",mapper.select(shop2).getDiscountStrategy());
		System.out.println(shop.getPay().getPay(1, 2, 3));
		TimeUnit.SECONDS.sleep(10);
	}

}
