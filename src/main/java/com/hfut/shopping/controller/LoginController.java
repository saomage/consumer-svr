package com.hfut.shopping.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hfut.shopping.domain.Consumer;
import com.hfut.shopping.feign.UserFeign;
import com.hfut.shopping.massage.ResultMsg;
import com.hfut.shopping.util.CookieUtil;
import com.hfut.shopping.util.JsonUtils;

@Controller
public class LoginController {

	@Autowired
	StringRedisTemplate redis;

	@Autowired
	UserFeign userFeign;

	@GetMapping("login")
	public String Login() {
		return "login";
	}

	@GetMapping("register")
	public String Register() {
		return "register";
	}

	@PostMapping("con/register")
	public String registerConsumer(Model model, Consumer consumer, HttpServletResponse resp) {
		ResultMsg msg = userFeign.consumerRegister(consumer);
		if (ResultMsg.successMsg == msg)
			return "login";
		else {
			model.addAttribute("error", msg);
			return "registerError";
		}
	}

	@PostMapping("con/login")
	public String loginConsumer(Consumer consumer, HttpServletResponse resp) {
		consumer = userFeign.consumerLogin(consumer);
		String uu = UUID.randomUUID().toString();
		redis.opsForValue().set(uu, JsonUtils.obj2String(consumer), 2, TimeUnit.HOURS);
		CookieUtil.set(resp, "consumer", uu, 7200);
		return "shopList";
	}

}
