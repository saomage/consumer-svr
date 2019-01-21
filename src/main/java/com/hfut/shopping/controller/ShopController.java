package com.hfut.shopping.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hfut.shopping.domain.Productor;
import com.hfut.shopping.domain.SimpleShop;
import com.hfut.shopping.service.ShopService;

@Controller
public class ShopController {

	@Autowired
	ShopService sservice;

	@GetMapping("consumer/shop/list")
	public ModelAndView list(SimpleShop shop) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("shops", sservice.simpleSelectAll(shop));
		mv.setViewName("shopList");
		return mv;
	}

	@GetMapping("consumer/shop/detailed")
	public ModelAndView shop(int id,HttpServletResponse resp) {
		SimpleShop shop = new SimpleShop();
		shop.setId(id);
		shop.setProductor(new Productor());
		shop=sservice.simpleSelect(shop);
		ModelAndView mv=new ModelAndView();
		mv.addObject("shop", shop);
		mv.setViewName("shop");
		return mv;
	}

}
