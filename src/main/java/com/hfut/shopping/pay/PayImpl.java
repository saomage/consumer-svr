package com.hfut.shopping.pay;

import org.springframework.stereotype.Component;

@Component
public class PayImpl implements Pay{
	@Override
	public int getPay(int d, int b, int c) {
		return d+b+c+1;
	}

}
