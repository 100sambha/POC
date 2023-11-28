package com.aithentic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aithentic.model.Customer;
import com.aithentic.model.CustomerInput;
import com.aithentic.services.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class CustomerController
{
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	@QueryMapping("customerInfo")
	public Customer customerInfo(@Argument int id)
	{
		return customerServiceImpl.getCustomerDetails(id);
	}
	
	@QueryMapping("allCustomers")
	public List<Customer> allCustomerInfo()
	{
		return customerServiceImpl.getAllCustomerDetails();
	}
	
	@MutationMapping("insertCustomer")
	public Customer insertCustomerInfo(@Argument CustomerInput c)
	{
		Customer customer = new Customer();
		customer.setCustomerName(c.getCustomerName());
		customer.setLicenseNo(c.getLicenseNo());
		customer.setStartDate(c.getStartDate());
		customer.setExpiryDate(c.getExpiryDate());
		customer.setStatus(c.isStatus());
		return customerServiceImpl.insertCustomerDetails(customer);
		
	}
	
	@QueryMapping("newCutomer")
	public Customer getUserTodoData(){
		Customer s = null;
		try {
			s = customerServiceImpl.getUserDetail();
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return s;
	}
}
