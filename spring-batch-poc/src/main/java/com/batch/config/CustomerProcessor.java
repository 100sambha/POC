package com.batch.config;

import org.springframework.batch.item.ItemProcessor;

import com.spring.batch.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer,Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {
        if(customer!=null) {
            return customer;
        }else{
            return null;
        }
    }
}