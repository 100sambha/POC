package com.dynamicpersistance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dynamicpersistance.model.Person;

public interface PersonRepo extends JpaRepository<Person, Integer> {

}
