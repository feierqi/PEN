/*******************************************************************************
 * Copyright (c) 2012 Team 3: Live Three or Die Hard
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team 3
 *******************************************************************************/

package test;

/**
 * use to test association between Example and Category
 */
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataStructure.BasicExample;
import dataStructure.Category;
import dataStructure.ICategory;
import dataStructure.IExample;
import database.Db4oDatabase;
import exceptions.DuplicateException;

import java.io.File;
import java.util.List;

/**
 * Test BasicExample-Category relationship
 * @author tpatikorn
 * @author iprangishvili
 * @author dmulcahy
 */
public class ExampleAndCategoryTest {
	private Db4oDatabase db;
	private String databaseName = "ExampleAndCategoryTest.yap";

	@Before
	public void setup() {
		// Connect to test database
		db = new Db4oDatabase(databaseName);
	}
	
	@Test
	public void testAddExamplePreAssignedID() throws DuplicateException {
		Category category1 = new Category("Cat", "Cats are nice");
		Category category2 = new Category("Dog", "Dogs are nice");
		BasicExample example1 = new BasicExample();
		example1.setCode("printf(Hello World);");
		example1.setTitle("Hello World");
		BasicExample example2 = new BasicExample();
		example2.setCode("printf(Bye World);");
		example2.setTitle("Bye World");
		db.store(category1);
		db.store(category2);
		db.store(example1);
		db.store(example2);
		example1.addCategory(category1);
		example1.addCategory(category2);
		db.store(category1);
		db.store(category2);
		db.store(example1);
		db.store(example2);

		//close and reopen
		db.close();
		db = new Db4oDatabase(databaseName);

		List<ICategory> categoryList = db.getExampleByID(example1.getId()).getCategories();
		assertNotSame((categoryList.get(0).getId().equals(category1.getId()) && categoryList.get(1).getId().equals(category2.getId()))
			    	, (categoryList.get(0).getId().equals(category2.getId()) && categoryList.get(1).getId().equals(category1.getId())));
	}

	@Test
	public void testAddExampleUnAssignedIDReStore() throws DuplicateException {
		Category category1 = new Category("Cat", "Cats are nice");
		Category category2 = new Category("Dog", "Dogs are nice");
		BasicExample example1 = new BasicExample();
		example1.setCode("printf(Hello World);");
		example1.setTitle("Hello World");
		BasicExample example2 = new BasicExample();
		example2.setCode("printf(Bye World);");
		example2.setTitle("Bye World");
		db.store(category1);
		db.store(category2);
		db.store(example1);
		db.store(example2);

		//close and reopen
		db.close();
		db = new Db4oDatabase(databaseName);

		IExample example3 = db.getByHeader("Hello World", null).get(0);
		IExample example4 = db.getByHeader("Bye World", null).get(0);
		ICategory category3 = db.getCategoryByID(category1.getId());
		ICategory category4 = db.getCategoryByID(category2.getId());

		example3.addCategory(category3);
		example3.addCategory(category4);
		example4.addCategory(category3);
		example4.addCategory(category4);
		
		db.store(example3);
		db.store(example4);
		db.store(category3);
		db.store(category4);

		//close and reopen
		db.close();
		db = new Db4oDatabase(databaseName);

		IExample newExample = db.getByHeader("Hello World", null).get(0);
		List<ICategory> categoryList = newExample.getCategories();
		assertNotSame((categoryList.get(0).getId().equals(category1.getId()) && categoryList.get(1).getId().equals(category2.getId()))
				, (categoryList.get(0).getId().equals(category2.getId()) && categoryList.get(1).getId().equals(category1.getId())));
	}

	@Test
	public void removeFromAllCategories() throws DuplicateException {
		Category category1 = new Category("Cat", "Cats are nice");
		Category category2 = new Category("Dog", "Dogs are nice");
		BasicExample example1 = new BasicExample();
		example1.assignId(1L);
		category1.assignId(2L);
		category2.assignId(3L);
		example1.addCategory(category1);
		example1.addCategory(category2);
		
		example1.removeFromAllCategories();
		assertEquals(category1.getExampleList().size(),0);
		assertEquals(category2.getExampleList().size(),0);
		assertEquals(example1.getCategories().size(),0);
	}

	@Test 
	public void duplicateTest() throws DuplicateException {
		Category category1 = new Category("Cat", "Cats are nice");
		Category category2 = new Category("Dog", "Dogs are nice");
		BasicExample example1 = new BasicExample();
		BasicExample example2 = new BasicExample();
		example1.assignId(11L);
		example2.assignId(12L);
		category1.assignId(21L);
		category2.assignId(22L);
		example1.addCategory(category1);
		try {
			example1.addCategory(category1);
			fail("It should throw exception");
		}
		catch(DuplicateException e) {
			//System.out.println(e.getMessage());
			assertEquals(e.getMessage(),"This example is already in category:Cat");
		}
	}

	@Test
	public void removeExamplesTest() throws DuplicateException {
		Category category1 = new Category("Cat", "Cats are nice");
		Category category2 = new Category("Dog", "Dogs are nice");
		BasicExample example1 = new BasicExample();
		BasicExample example2 = new BasicExample();
		example1.assignId(11L);
		example2.assignId(12L);
		category1.assignId(21L);
		category2.assignId(22L);
		example1.addCategory(category1);
		example1.addCategory(category2);
		example2.addCategory(category1);
		example2.addCategory(category2);
		
		category1.removeAllExamples();
		assertEquals(category1.getExampleList().size(),0);
		assertEquals(category2.getExampleList().size(),2);
		assertEquals(example1.getCategories().size(),1);
		assertEquals(example2.getCategories().size(),1);
	}	

	@After
	public void cleanup() {
		db.close();

		//Delete the database. We don't need it anymore.
		File f = new File(databaseName);
		if (f.exists())
			f.delete();
	}
}
