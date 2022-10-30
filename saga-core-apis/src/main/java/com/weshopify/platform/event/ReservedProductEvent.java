package com.weshopify.platform.event;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReservedProductEvent implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int productId;
	private int quantity;
	private String aggregateId;
	
	/**
	 * if product micro service will reserve the products as per the quantity
	 * this status will be true otherwise status will be false
	 * if the status is false we have to trigger compensate transaction
	 */
	private boolean status;
}
