package com.hfut.shopping.LFU;

import org.springframework.stereotype.Component;

@Component
public interface LFU<V,E> {

	void add(V v,E e);
	
	E get(V v);
	
}
