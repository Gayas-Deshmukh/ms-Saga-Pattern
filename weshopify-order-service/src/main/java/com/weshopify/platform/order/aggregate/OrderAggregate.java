package com.weshopify.platform.order.aggregate;

import java.util.Date;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.weshopify.platform.command.PlaceOrderCommand;
import com.weshopify.platform.command.ProductUpdatedCommand;
import com.weshopify.platform.command.ReservedProductCommand;
import com.weshopify.platform.event.PlaceOrderEvent;
import com.weshopify.platform.event.ProductUpdatedEvent;
import com.weshopify.platform.event.ReservedProductEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aggregate
public class OrderAggregate 
{	
	@AggregateIdentifier
	private String aggregateId;
	
	private int 	orderId;
	private int 	productId;
	private Date 	billDate;
	private String	orderStatus;
	private int 	orderQuantity;
	private int     reservedProductQuantity;
	private double  totalProductPrice;
	private boolean productReserved;
	private int     remainingAvailableProduct;
	
	@CommandHandler
	public OrderAggregate (PlaceOrderCommand command)
	{
		log.info("Order Command handler");
		
		PlaceOrderEvent event = new PlaceOrderEvent();
		
		BeanUtils.copyProperties(command, event);
		
		event.setAggregateId(UUID.randomUUID().toString());
		
		AggregateLifecycle.apply(event);
	}
	
	@CommandHandler
	public OrderAggregate (ReservedProductCommand reservedCommand)
	{
		log.info("Invoked Reserved Product Command Handler");
		
		ReservedProductEvent reservedEvent = new ReservedProductEvent();
		reservedEvent.setProductId(reservedCommand.getProductId());
		reservedEvent.setQuantity(reservedCommand.getQuantity());
		reservedEvent.setAggregateId(UUID.randomUUID().toString());
		
		AggregateLifecycle.apply(reservedEvent);
	}
	
	@CommandHandler
	public void OrderAggregate(ProductUpdatedCommand pUpdatedCommand)
	{
		ProductUpdatedEvent updatedProductEvent = new ProductUpdatedEvent();
		
		updatedProductEvent.setAggreagteId(UUID.randomUUID().toString());
		updatedProductEvent.setProductId(pUpdatedCommand.getProductId());
		updatedProductEvent.setQuantityUpdated(pUpdatedCommand.getQuantityUpdated());
		updatedProductEvent.setRemainingQuantity(pUpdatedCommand.getRemainingQuantity());
	
		AggregateLifecycle.apply(updatedProductEvent);
	}
	
	@EventHandler
	public void omPublishPlaceOrderEvent(PlaceOrderEvent event)
	{
		log.info("Default event handler");
		
		this.aggregateId 		= event.getAggregateId();
		this.orderId			= event.getOrderId();
		this.productId  		= event.getProductId();
		this.billDate 			= event.getBillDate();
		this.orderStatus 		= event.getOrderStatus();
		this.orderQuantity 		= event.getOrderQuantity();
		this.totalProductPrice	= event.getTotalProductPrice();		
	}
	
	@EventHandler
	public void onPublishReservedProductEvent(ReservedProductEvent event)
	{
		this.aggregateId     			= event.getAggregateId();
		this.productId					= event.getProductId();
		this.reservedProductQuantity	= event.getQuantity();
		this.productReserved	 		= event.isStatus();
	}
	
	@EventHandler
	public void onPublishUpdateProductEvent(ProductUpdatedEvent event)
	{
		this.aggregateId     			= event.getAggreagteId();
		this.productId					= event.getProductId();
		this.remainingAvailableProduct	= event.getRemainingQuantity();
		this.orderQuantity	 			= event.getQuantityUpdated();
	}
}
