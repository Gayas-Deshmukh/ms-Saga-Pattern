package com.weshopify.platform.order.saga;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.weshopify.platform.command.ReservedProductCommand;
import com.weshopify.platform.event.PlaceOrderEvent;

import lombok.extern.slf4j.Slf4j;

@Saga
@Slf4j
public class OrderSaga 
{
	@Autowired
	private CommandGateway commandGateway;
	
	@SagaEventHandler(associationProperty = "aggregateId")
	@StartSaga
	public void onPlaceOrderEvent(PlaceOrderEvent placeOrderEvent)
	{
	   log.info("Order saga place order event triggered");
	   
	   //Step-1 Prepare the Reserve Product command
	   ReservedProductCommand reservedProductCommand = new ReservedProductCommand();
	   reservedProductCommand.setProductId(placeOrderEvent.getProductId());
	   reservedProductCommand.setQuantity(placeOrderEvent.getOrderQuantity());
	
	  // Step-2 send the reserved product command to product service command handler
	   commandGateway.send(reservedProductCommand,new CommandCallback<ReservedProductCommand, Object>() {
	    
		@Override
	    public void onResult(CommandMessage<? extends ReservedProductCommand> commandMessage,
	    		CommandResultMessage<? extends Object> commandResultMessage) {
			if(commandResultMessage.isExceptional())
			{
				log.info("Trigger the compensating transaction");
			}
	    }
	   });
	}
}
