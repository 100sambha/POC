package com.aithentic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aithentic.model.Customer;
import com.aithentic.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class CustomerServiceImpl
{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Customer getUserDetail()  throws JsonProcessingException 
	{
		Customer customer = restTemplate.getForObject("https://dummyjson.com/todos/9",Customer.class);
		customerRepository.save(customer);
		System.out.println(customer.getCustomerName());
		return customer;
	}
	
	
	public Customer getCustomerDetails(int id)
	{
		return customerRepository.findById(id).get();
	}
	
	public List<Customer> getAllCustomerDetails()
	{
		return customerRepository.findAll();
	}
	
	public Customer insertCustomerDetails(Customer customer)
	{
		return customerRepository.save(customer);
	}
}