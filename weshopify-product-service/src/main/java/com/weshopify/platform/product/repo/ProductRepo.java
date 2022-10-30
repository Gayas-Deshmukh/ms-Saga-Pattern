package com.weshopify.platform.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weshopify.platform.product.domain.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>
{

}
