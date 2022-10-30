package com.weshopify.platform.order.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GeneratorType;

import lombok.Data;

@Data
@Entity
@Table(name="orders")
public class Order implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int 	orderId;
	
	private int 	productId;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date 	billDate;
	private String	orderStatus;
	private int 	orderQuantity;
	private double  totalProductPrice;
}
