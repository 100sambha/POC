package com.dynamicpersistance;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dynamicpersistance.config.JpaEntityManagerFactory;
import com.dynamicpersistance.model.Person;
import com.dynamicpersistance.service.PersonService;

public class App {
    public static void main(String[] args) {
        JpaEntityManagerFactory.entityManagerFactory("dev");
//        JpaEntityManagerFactory.entityManagerFactory(System.getenv("dbEnvironment"));
        
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
		appContext.scan("com.dynamicpersistance");
		appContext.refresh();

		PersonService personServiceApi = (PersonService) appContext.getBean("personService");
		personServiceApi.saveData(new Person("sam", 26));
		personServiceApi.getData();
		
		appContext.close();
//        String environment = env;
        
        // Change this to select the desired environment (e.g., "production", "qa", "dev", or "uat")

//        // Create an EntityManager from the EntityManagerFactory
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        // Create and persist a new entity
//        entityManager.getTransaction().begin();
//
//        try {
//            // Insert data into the database
//            Person person = new Person("John Doe", 30);
//            entityManager.persist(person);
//
//            // Update data in the database
//            person.setAge(31);
//            entityManager.merge(person);
//
//            entityManager.getTransaction().commit();
//        } catch (Exception e) {
//            entityManager.getTransaction().rollback();
//            e.printStackTrace();
//        }
//
//        // Retrieve data from the database
//        Person retrievedPerson = entityManager.find(Person.class, 1L); // Assuming 1L is the ID of the person you want to retrieve
//
//        if (retrievedPerson != null) {
//            System.out.println("Retrieved person: " + retrievedPerson.getName() + " (Age: " + retrievedPerson.getAge() + ")");
//        } else {
//            System.out.println("Person with ID 1 not found.");
//        }
//
//        // Close the EntityManager and EntityManagerFactory
//        entityManager.close();
//        entityManagerFactory.close();
    }
}