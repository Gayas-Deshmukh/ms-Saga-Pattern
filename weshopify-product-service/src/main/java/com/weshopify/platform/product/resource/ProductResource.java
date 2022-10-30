package com.weshopify.platform.product.resource;

import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.weshopify.platform.command.ProductCommand;
import com.weshopify.platform.product.bean.ProductBean;
import com.weshopify.platform.product.service.ProductServiceImpl;


@RestController
public class ProductResource 
{
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private transient CommandGateway commandGateway;
	
	@PostMapping(value = "/products")
	public ResponseEntity<ProductBean> createProduct(@RequestBody ProductBean productDate)
	{
		return ResponseEntity.status(HttpStatus.OK).body(productService.createProduct(productDate));
	}
	
	@PutMapping(value = "/products")
	public ResponseEntity<ProductBean> updateProduct(@RequestBody ProductBean productData)
	{
		ProductBean productbean = productService.updateProduct(productData);
		
		if (productbean != null)
		{
			ProductCommand command  = new ProductCommand();
			
			BeanUtils.copyProperties(productbean, command);
			
			commandGateway.send(command);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(productbean);
		
	}
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<ProductBean> getProductById(@PathVariable("id") int id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(productService.getproductById(id));
	}
	
	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductBean>> getAllProducts()
	{
		return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
	}
}
