package com.skilldistillery.overflow.entitiesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.skilldistillery.overflow.entities.Address;

class AddressTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private Address address;
	
	@BeforeEach
	void setUp() throws Exception {
		emf = Persistence.createEntityManagerFactory("Overflow");
		em = emf.createEntityManager();
		address = em.find(Address.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		emf.close();
		address = null;
	}

	@Test
	void test() {
		assertEquals("123 Hello World Lane", address.getStreet());
		assertEquals("Denver", address.getCity());
	}

}
