package com.weshopify.platform.order.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weshopify.platform.order.bean.Orderbean;
import com.weshopify.platform.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OrderResource 
{
	@Autowired
	private OrderService orderService;
	
	@PostMapping(value = "/orders")
	public ResponseEntity<Orderbean> placeOrder (@RequestBody Orderbean orderBean)
	{
		return ResponseEntity.status(HttpStatus.OK).body(orderService.placeOrder(orderBean));
	}
	
}
