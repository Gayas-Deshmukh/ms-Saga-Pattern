package com.weshopify.platform.order.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Orderbean implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int 	orderId;
	private int 	productId;
	private Date 	billDate;
	private String	orderStatus;
	private int 	orderQuantity;
	private double  totalProductPrice;
}
