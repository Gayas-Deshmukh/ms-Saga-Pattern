package com.weshopify.platform.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weshopify.platform.order.domain.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> 
{

}
