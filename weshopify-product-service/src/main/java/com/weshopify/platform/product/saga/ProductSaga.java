package com.weshopify.platform.product.saga;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.weshopify.platform.command.ProductUpdatedCommand;
import com.weshopify.platform.event.ReservedProductEvent;
import com.weshopify.platform.product.domain.Product;
import com.weshopify.platform.product.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;


@Saga
@Slf4j
public class ProductSaga 
{
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CommandGateway gateway;
	
	@SagaEventHandler(associationProperty = "aggregateId")
	@StartSaga
	public void onReservedProductEvent(ReservedProductEvent rpEvent)
	{
		Product dbProduct = productRepo.findById(rpEvent.getProductId()).get();
		
		log.info("Available Qty :" + dbProduct.getQuantity());
		log.info("Qty to be reserve:" + rpEvent.getQuantity());
		
		if (dbProduct.getQuantity() > rpEvent.getQuantity())
		{
			int	updatedQty	= dbProduct.getQuantity() - rpEvent.getQuantity();
		    dbProduct.setQuantity(updatedQty);
		    productRepo.save(dbProduct);
		    
		    log.info("Remaining Qty :" + dbProduct.getQuantity());
		}
		
		ProductUpdatedCommand updatedCmd = new ProductUpdatedCommand();
		updatedCmd.setProductId(dbProduct.getProductId());
		updatedCmd.setQuantityUpdated(rpEvent.getQuantity());
		updatedCmd.setRemainingQuantity(dbProduct.getQuantity());
		
		gateway.send(updatedCmd, new CommandCallback<ProductUpdatedCommand, Object>() {
			@Override
			public void onResult(CommandMessage<? extends ProductUpdatedCommand> commandMessage,
					CommandResultMessage<? extends Object> commandResultMessage) {
				if (commandResultMessage.isExceptional())
				{
					log.info("Trigger the compensating Transactionn");
				}
				
			}
		});
	}
}
