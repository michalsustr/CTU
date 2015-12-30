/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbtest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author michal
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Entity manager and transaction
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbtest");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
Source.class
		//create new entity and persist it to the database
		Book book = Book.createBook(123L, "JPA 2.0 - Mastering the Java Persistence API",
			"Kompletni pruvodce JPA 2.0",532);
		tx.begin();
		em.persist(book);
		tx.commit();

		//finding by ID
		Book booka = em.find(Book.class, 123L);
		System.out.println("Finding book> "+booka);

		//removing an entity
		Book bookrem = Book.createBook(124L, "Java", null, null);
		tx.begin();
		em.persist(bookrem);
		tx.commit();
			Book bookf = em.find(Book.class, 124L);
			System.out.println("Remove book before> "+bookf);
		tx.begin();
		em.remove(bookrem);
		tx.commit();
			Book bookg = em.find(Book.class, 124L);
			System.out.println("Remove book after> "+bookg);

		//merging an entity outside transaction
		Book bookmer = Book.createBook(125L, "Java", null, null);
		tx.begin();
		em.persist(bookmer);
		tx.commit();
		em.clear(); //demonstrate another work with database
			bookmer.setDescription("Zase nejaka Java");
			Book bookh = em.find(Book.class, 125L);
			System.out.println("Merging book before> "+bookh);
		tx.begin();
		em.merge(bookmer);
		tx.commit();
			Book booki = em.find(Book.class, 125L);
			System.out.println("Merging book after> "+booki);

		//update an entity inside transanction
		Book booku = Book.createBook(126L, "Java 6", null, null);
		tx.begin();
		em.persist(booku);
		booku.setDescription("No a jak jinak, zase Java.");
		tx.commit();
			Book bookj = em.find(Book.class, 126L);
			System.out.println("Update book> "+bookj);
    }

}
