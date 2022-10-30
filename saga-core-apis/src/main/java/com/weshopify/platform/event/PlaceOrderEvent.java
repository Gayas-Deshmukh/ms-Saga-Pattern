package com.weshopify.platform.event;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class PlaceOrderEvent implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String aggregateId;
	
	private int 	orderId;
	private int 	productId;
	private Date 	billDate;
	private String	orderStatus;
	private int 	orderQuantity;
	private double  totalProductPrice;
}
