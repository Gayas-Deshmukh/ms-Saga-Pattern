package com.weshopify.platform.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weshopify.platform.product.bean.ProductBean;
import com.weshopify.platform.product.domain.Product;
import com.weshopify.platform.product.repo.ProductRepo;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class ProductServiceImpl implements ProductService 
{
	@Autowired
	ProductRepo productRepo;
	
	@Override
	public ProductBean createProduct(ProductBean productBean) 
	{
		Product product = productRepo.save(mapBeanToDomain(productBean));
		return mapDomainToBean(product);
	}

	@Override
	public ProductBean updateProduct(ProductBean productBean) {
		Product product = productRepo.save(mapBeanToDomain(productBean));
		return mapDomainToBean(product);
	}

	@Override
	public ProductBean getproductById(int productId) 
	{
		Product product = productRepo.getReferenceById(productId);
		
		if (product != null)
		{
			return mapDomainToBean(product);
		}
		else
		{
			throw new RuntimeException("No Product found with given ProductID\t"+ productId );
		}
	}

	@Override
	public List<ProductBean> getAllProducts() 
	{
		List<Product> 		products			=	productRepo.findAll();
		List<ProductBean> 	productsBeanList	= 	new ArrayList<>();
		
		products.stream().forEach(product ->{
			productsBeanList.add(mapDomainToBean(product));
		});
		
		if (!productsBeanList.isEmpty())
		{
			return productsBeanList;
		}
		else
		{
			throw new RuntimeException("No Products are Available");
		}
	}

	private ProductBean mapDomainToBean(Product product)
	{
		ProductBean productBean	=	new ProductBean();
		BeanUtils.copyProperties(product, productBean);
		return productBean;
	}
	
	private Product mapBeanToDomain(ProductBean productBean)
	{
		Product	product	=	new Product();
		BeanUtils.copyProperties(productBean, product);
		return product;
	}
}
