package com.weshopify.platform.event;

import lombok.Data;

@Data
public class ProductUpdatedEvent
{
	private int quantityUpdated;
	private int remainingQuantity;
	private int productId;
	private String aggreagteId;
}
