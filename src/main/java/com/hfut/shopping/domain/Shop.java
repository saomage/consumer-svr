package com.hfut.shopping.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.hfut.shopping.classLoader.ShopClassLoader;
import com.hfut.shopping.pay.Pay;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Component
@Data
@EqualsAndHashCode(callSuper = false)
public class Shop extends SimpleShop {

	private byte[] discountStrategy;

	private String countPay;

	private ShopClassLoader classLoader;

	private Pay pay;

	public Shop(String name, byte[] discountStrategy)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
		this.discountStrategy = discountStrategy;
		this.name = name;
		if (discountStrategy != null) {
			classLoader = new ShopClassLoader(discountStrategy);
			Class<?> aClass = classLoader.loadClass("com.hfut.shopping.pay.PayImpl");
			Object newInstance = aClass.newInstance();
			Method method = aClass.getMethod("getPay", int.class,int.class,int.class);
			pay=(int a,int b,int c)->{
				try {
					return (int)method.invoke(newInstance, a,b,c);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 0;
			};
		}
	}
	
	public Shop() {
		super();
	}

}
