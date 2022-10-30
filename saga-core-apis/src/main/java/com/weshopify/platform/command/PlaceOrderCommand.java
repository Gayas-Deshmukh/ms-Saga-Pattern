package com.weshopify.platform.command;

import java.io.Serializable;
import java.util.Date;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
public class PlaceOrderCommand implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@TargetAggregateIdentifier
	private int 	orderId;
	private int 	productId;
	private Date 	billDate;
	private String	orderStatus;
	private int 	orderQuantity;
	private double  totalProductPrice;
}
