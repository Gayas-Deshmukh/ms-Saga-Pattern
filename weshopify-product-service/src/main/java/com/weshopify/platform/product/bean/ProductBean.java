package com.weshopify.platform.product.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductBean implements Serializable
{

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	
	private int productId;
	private int quantity;
	private String name;
	private double price;
	private boolean status;

}
