package com.hfut.shopping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hfut.shopping.domain.CartMsg;
import com.hfut.shopping.domain.Commodity;
import com.hfut.shopping.domain.Consumer;
import com.hfut.shopping.domain.Order;
import com.hfut.shopping.domain.ShopOrder;
import com.hfut.shopping.mapper.CartMapper;
import com.hfut.shopping.mapper.CommodityMapper;
import com.hfut.shopping.mapper.OrderMapper;
import com.hfut.shopping.mapper.ShopMapper;
import com.hfut.shopping.massage.ResultMsg;
import com.hfut.shopping.util.RedisLock;

@Controller
public class CartController {

	@Autowired
	CommodityMapper cmapper;

	@Autowired
	CartMapper tmapper;
	
	@Autowired
	RedisLock lock;
	
	@Autowired
	OrderMapper omapper;
	
	@Autowired
	ShopMapper smapper;

	@GetMapping("consumer/addCommodity")
	@ResponseBody
	public ResultMsg addCommodity(Integer shopId, Integer commodityId, String shopName, HttpServletRequest req) {
		try {
			Commodity commodity = cmapper.selectCommodity(commodityId);
			CartMsg cartMsg = new CartMsg();
			cartMsg.setCommodityId(commodityId);
			cartMsg.setShopId(shopId);
			cartMsg.setShopName(shopName);
			cartMsg.setType(commodity.getType());
			cartMsg.setPrice(commodity.getType());
			cartMsg.setCommodityName(commodity.getName());
			cartMsg.setConsumerId(((Consumer) req.getAttribute("consumer")).getId());
			tmapper.insertCartMsg(cartMsg);
			return ResultMsg.successMsg;
		} catch (Exception e) {
			return ResultMsg.errorMsg.setMsg(e.getMessage());
		}
	}

	@GetMapping("consumer/shoppingCart")
	public ModelAndView shoppingCart(HttpServletRequest req) {
		Consumer consumer = (Consumer) req.getAttribute("consumer");
		CartMsg cartMsg = new CartMsg();
		cartMsg.setConsumerId(consumer.getId());
		List<CartMsg> cart = tmapper.selectCartMsg(cartMsg);
		ModelAndView mv = new ModelAndView();
		mv.addObject("cart", cart);
		mv.setViewName("cart");
		return mv;
	}
	
	@GetMapping("consumer/delete")
	@ResponseBody
	public ResultMsg delete(Integer cartMsgId) {
		try {
			tmapper.delete(cartMsgId);
			return ResultMsg.successMsg;
		} catch (Exception e) {
			return ResultMsg.errorMsg.setMsg(e.getMessage());
		}
	}
	
	@GetMapping("consumer/submit")
	@ResponseBody
	public ResultMsg submit(HttpServletRequest req) {
		Order order = new Order();
		Consumer consumer = (Consumer)req.getAttribute("consumer");
		List<ShopOrder> orders = tmapper.selectOrder(consumer.getId());
		orders.stream().map(e->{
			try {
				return e.getPrice(smapper);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
			return null;
		}).reduce((i,j)->i+j).ifPresent(order::setPrice);
		order.setConsumerId(consumer.getId());
		lock.Lock(consumer.getId().toString(), 60L);
		int orderId=0;
		try {
			orderId = omapper.insertOrder(order);
		} finally {
			lock.unLock(consumer.getId().toString());
		}
		if(orderId>0) {
			omapper.removeToOrder(consumer.getId(), orderId);
			return ResultMsg.successMsg;
		}
		return ResultMsg.errorMsg;
	}
	
	
}
