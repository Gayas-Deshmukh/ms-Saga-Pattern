package com.weshopify.platform.event;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductEvent implements Serializable
{

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	
	private String aggregateId;
	private int productId;
	private int quantity;
	private String name;
	private double price;
	private boolean status;

}
