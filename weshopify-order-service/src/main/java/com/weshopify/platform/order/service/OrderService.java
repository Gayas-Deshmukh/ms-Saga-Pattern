package com.weshopify.platform.order.service;

import com.weshopify.platform.order.bean.Orderbean;

public interface OrderService 
{
	public Orderbean placeOrder(Orderbean bean);
}
