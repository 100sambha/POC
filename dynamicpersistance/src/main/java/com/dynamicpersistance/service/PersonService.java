package com.dynamicpersistance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dynamicpersistance.model.Person;
import com.dynamicpersistance.repo.PersonRepo;

@Transactional
@Service("personService")
public class PersonService {

	@Autowired
	PersonRepo personRepo;
	
	public void getData() {
		System.out.println(personRepo.findAll().toString());;
	}
	
	public void saveData(Person person) {
		personRepo.save(person);
		System.out.println("Saved");
	}
}
