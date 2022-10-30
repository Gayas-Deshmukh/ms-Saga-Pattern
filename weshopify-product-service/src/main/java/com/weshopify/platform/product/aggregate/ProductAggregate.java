package com.weshopify.platform.product.aggregate;

import java.io.Serializable;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.weshopify.platform.command.ProductCommand;
import com.weshopify.platform.command.ReservedProductCommand;
import com.weshopify.platform.event.ProductEvent;
import com.weshopify.platform.event.ReservedProductEvent;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class ProductAggregate implements Serializable
{
	//private static final long serialVersionUID = 1L;
	
	@AggregateIdentifier
	private String aggregateId;
	
	private int productId;
	private int quantity;
	private String name;
	private double price;
	private boolean status;
	private int quantityToBereserved;
	
	@CommandHandler
	public ProductAggregate (ProductCommand command)
	{
		
		log.info("Command Handler with command \t" + command.toString());
		ProductEvent event = new ProductEvent();
		
		BeanUtils.copyProperties(command, event);
		
		event.setAggregateId(UUID.randomUUID().toString());
		
		AggregateLifecycle.apply(event);
	}
	
	@CommandHandler
	public ProductAggregate (ReservedProductCommand reservedPcommand)
	{
		// Command received by the product service command handler, converting it into ReservProductEvent
		ReservedProductEvent rpEvent = new ReservedProductEvent();
		
		rpEvent.setAggregateId(UUID.randomUUID().toString());
		rpEvent.setProductId(reservedPcommand.getProductId());
		rpEvent.setQuantity(reservedPcommand.getQuantity());
		rpEvent.setStatus(false);
		
		AggregateLifecycle.apply(rpEvent);
	}
	
	@EventSourcingHandler
	public void onPublishevent(ProductEvent event)
	{
		log.info("Defualt event source handler");

		 this.aggregateId = event.getAggregateId();
		 this.name			=	event.getName();
		 this.price			=	event.getPrice();
		 this.productId		=	event.getProductId();
		 this.quantity		=	event.getQuantity();
		 this.status		=	event.isStatus();
	}
	
	@EventSourcingHandler
	public void onReserveProductEventPublish(ReservedProductEvent rpEvent)
	{
		log.info("ReservedProduct Event recieved by the default event handler");
		this.aggregateId 			= rpEvent.getAggregateId();
		this.quantityToBereserved	= rpEvent.getQuantity();
		this.productId 				= rpEvent.getProductId();
	}
	 
}
