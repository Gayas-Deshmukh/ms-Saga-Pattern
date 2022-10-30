package com.weshopify.platform.command;

import java.io.Serializable;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class ReservedProductCommand implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@TargetAggregateIdentifier
	private int productId;
	private int quantity;

}
