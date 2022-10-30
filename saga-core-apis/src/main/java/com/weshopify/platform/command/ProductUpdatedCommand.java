package com.weshopify.platform.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class ProductUpdatedCommand 
{
	@TargetAggregateIdentifier
	private int productId;
	
	private int quantityUpdated;
	private int remainingQuantity;
}
