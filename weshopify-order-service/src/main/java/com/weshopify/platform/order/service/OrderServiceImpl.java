package com.weshopify.platform.order.service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weshopify.platform.command.PlaceOrderCommand;
import com.weshopify.platform.command.ReservedProductCommand;
import com.weshopify.platform.order.bean.Orderbean;
import com.weshopify.platform.order.domain.Order;
import com.weshopify.platform.order.repo.OrderRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private CommandGateway commandGateway;
	
	@Override
	public Orderbean placeOrder(Orderbean bean) 
	{
		/**
		 * Reserve the product before placing it
		 */
		/*
		ReservedProductCommand reservedProductCmd = new ReservedProductCommand();
		
		reservedProductCmd.setProductId(bean.getProductId());
		reservedProductCmd.setQuantity(bean.getOrderQuantity());
		
		CompletableFuture<ReservedProductCommand> reservedProductFeture = commandGateway.send(reservedProductCmd);
		
		if(!reservedProductFeture.isDone())
		{
			log.info("Reserved Product Command Undelivered");
			
			throw new RuntimeException("Reserved Product Undelivedred to CommandHandler");
		}
		*/
		
		PlaceOrderCommand command = new PlaceOrderCommand();
		
		BeanUtils.copyProperties(bean, command);
		
	    commandGateway.send(command ,new CommandCallback<PlaceOrderCommand, Object>() {
	    	@Override
			public void onResult(CommandMessage<? extends PlaceOrderCommand> commandMessage,
					CommandResultMessage<? extends Object> commandResultMessage) {
				if(commandResultMessage.isExceptional())
				{
					log.info("Trigger the compensating trasaction");
				}
				
			}
	    });

		Order orderDomain	=	mapBeanToDomain(bean);
		orderRepo.save(orderDomain);
		
		if(orderDomain.getOrderId() > 0)
		{
			orderDomain.setOrderStatus("COMPLETED");
			orderRepo.save(orderDomain);		
			return mapDomainToBean(orderDomain);
		}
		else
		{
			throw new RuntimeException("Order Placing Falied");
		}
	}
	
	private Order mapBeanToDomain(Orderbean orderBean)
	{
		Order order = new Order();
		BeanUtils.copyProperties(orderBean, order);
		order.setBillDate(new Date());
		order.setOrderStatus("INITIATED");
		return order;
	}
	
	private Orderbean mapDomainToBean(Order order)
	{
		Orderbean orderBean = new Orderbean();
		BeanUtils.copyProperties(order, orderBean);
		orderBean.setOrderStatus("COMPLETED");
		return orderBean;
	}

}
