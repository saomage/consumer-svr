package com.hfut.shopping.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hfut.shopping.domain.Consumer;
import com.hfut.shopping.massage.ResultMsg;

@FeignClient(name="user-svr")
public interface UserFeign {

	@PostMapping("consumer/login")
	Consumer consumerLogin(@RequestBody Consumer consumer);
	
	@PostMapping("consumer/register")
	ResultMsg consumerRegister(@RequestBody Consumer consumer);
}
