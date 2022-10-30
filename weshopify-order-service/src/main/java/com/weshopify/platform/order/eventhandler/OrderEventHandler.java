package com.weshopify.platform.order.eventhandler;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.context.annotation.Configuration;
import com.weshopify.platform.event.ProductUpdatedEvent;
import com.weshopify.platform.order.repo.OrderRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ProcessingGroup(value = "order-group")
public class OrderEventHandler 
{
	private OrderRepo orderRepo;
	
    public OrderEventHandler(OrderRepo orderrepo)
	{
		this.orderRepo = orderrepo;
	}
	
   @EventHandler
   public void onUpdateOrderEvent(ProductUpdatedEvent event)
   {
	   log.info("In Order Event Handler");
   }
}
