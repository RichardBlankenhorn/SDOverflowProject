package com.skilldistillery.overflow.entitiesTests;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.skilldistillery.overflow.entities.Technology;

class TechnologyTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private Technology technology;
	
	@BeforeEach
	void setUp() throws Exception {
		emf = Persistence.createEntityManagerFactory("EventTrackerProject");
		em = emf.createEntityManager();
		technology = em.find(Technology.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		emf.close();
		technology = null;
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
