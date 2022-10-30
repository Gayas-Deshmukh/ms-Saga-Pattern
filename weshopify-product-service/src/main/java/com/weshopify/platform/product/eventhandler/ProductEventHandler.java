package com.weshopify.platform.product.eventhandler;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.weshopify.platform.event.ReservedProductEvent;
import com.weshopify.platform.product.domain.Product;
import com.weshopify.platform.product.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@ProcessingGroup(value = "product-group")
public class ProductEventHandler 
{
	@Autowired
	private ProductRepo productRepo;
	
	@EventHandler
	public void updateQuantity(ReservedProductEvent reservedProductEvent)
	{
		log.info("Updating Quantity in Handler");
		
		Product dbProduct = productRepo.findById(reservedProductEvent.getProductId()).get();
		
		log.info("Available Qty :" + dbProduct.getQuantity());
		log.info("Qty to be reserve:" + reservedProductEvent.getQuantity());
		
		if (dbProduct.getQuantity() > reservedProductEvent.getQuantity())
		{
			int	updatedQty	= dbProduct.getQuantity() - reservedProductEvent.getQuantity();
		    dbProduct.setQuantity(updatedQty);
		    productRepo.save(dbProduct);
		    
		    log.info("Remaining Qty :" + dbProduct.getQuantity());
		}
	}
}
