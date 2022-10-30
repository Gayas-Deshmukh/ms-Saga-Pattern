package com.weshopify.platform.product.service;

import java.util.List;

import com.weshopify.platform.product.bean.ProductBean;

public interface ProductService 
{
	public ProductBean createProduct(ProductBean productBean);
	public ProductBean updateProduct(ProductBean productBean);
	public ProductBean getproductById(int productId);
	public List<ProductBean> getAllProducts();
}
