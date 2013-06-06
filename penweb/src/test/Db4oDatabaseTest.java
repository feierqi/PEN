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

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataStructure.*;
import database.Db4oDatabase;

/**
 * Test functions in db4odatabase.java
 * @author tpatikorn
 * @author iprangishvili
 * @author dmulcahy
 */
public class Db4oDatabaseTest {
	private Db4oDatabase testee;
	private String databaseName = "db4otest.yap";

	@Before
	public void setup() {
		// Connect to test database
		testee = new Db4oDatabase(databaseName);
	}

	@Test
	public void testGetByHeaderWithTitleAndAuthor() {
		BasicExample entry = new BasicExample();
		User u = new User("anAuthor", "", "");
		entry.setTitle("aTitle");
		entry.assignOwner(u);
		testee.store(entry);

		assertEquals(testee.getByHeader("aTitle", u).get(0).getTitle(),
				entry.getTitle());
	}

	@Test
	public void testGetByHeaderWithTitleOnly() {
		BasicExample entry = new BasicExample();
		entry.setTitle("aTitle");
		testee.store(entry);

		assertEquals(testee.getByHeader("aTitle", null).get(0).getTitle(),
				entry.getTitle());
	}

	@Test
	public void testGetByHeaderWithAuthorOnly() {
		BasicExample entry = new BasicExample();
		User u = new User("anAuthor","","");
		entry.assignOwner(u);
		testee.store(entry);
		String name = testee.getByHeader(null, u).get(0).getOwner().getDisplayName();
		assertEquals(name, entry.getOwner().getDisplayName());
	}

	@Test
	public void testGetByKeyword() {
		// TODO: Implement when the actual function is implemented
	}

	@Test
	public void testedit() {
		BasicExample entry = new BasicExample();
		entry.setCode("Hello world!");
		testee.store(entry);

		entry.setTitle("itworks!");

		BasicExample entry2 = new BasicExample();

		List<IExample> list = testee.getByHeader("itworks!", null);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof BasicExample) {
				entry2 = (BasicExample) list.get(i);
				break;
			}
		}
		assertEquals(entry2.getCode(), "Hello world!");
	}
	
	@Test
	public void testgetNewIdunassigned() {
		BasicExample entry1 = new BasicExample();
		
		testee.store(entry1);
		
		assertNotSame(entry1.getId(), -1L);
	}

	@Test
	public void testgetNewIdassigned() {
		BasicExample entry1 = new BasicExample();
		Long id = 55L;
		entry1.assignId(id);
		
		assertEquals(entry1.getId(), id);
	}
	
	@Test
	public void testGetByIDfound() {
		BasicExample entry1 = new BasicExample();
		BasicExample entry2 = new BasicExample();
		BasicExample entry3 = new BasicExample();
		BasicExample entry4 = new BasicExample();
		BasicExample entry5 = new BasicExample();

		testee.store(entry1);
		testee.store(entry2);
		testee.store(entry3);
		testee.store(entry4);
		testee.store(entry5);
		
		assertEquals(testee.getByID(entry5.getId()), entry5);
	}
	
	@Test
	public void testGetByIDnotfound() {
		assertEquals(testee.getByID(-1L), null);
	}
	

	@Test
	public void testGetTitleList() {

		Category entry1 = new Category("Live",null);
		Category entry2 = new Category("Three",null);
		Category entry3 = new Category("Or",null);
		Category entry4 = new Category("Die",null);
		Category entry5 = new Category("Hard",null);
		
		testee.store(entry1);
		testee.store(entry2);
		testee.store(entry3);
		testee.store(entry4);
		testee.store(entry5);
		
		ArrayList<String> list = testee.listCategoryNames();
		String name = list.get(0)+list.get(1)+list.get(2)+list.get(3)+list.get(4);
		assertEquals(name,"LiveThreeOrDieHard");
		
		assertTrue(list.contains("Live"));
		assertTrue(list.contains("Three"));
		assertTrue(list.contains("Or"));
		assertTrue(list.contains("Die"));
		assertTrue(list.contains("Hard"));
		
	}

	@Test
	public void getUserByIdTest() {
		User Alice = new User("Alice1234","changeMe123","Alice");
		testee.store(Alice);
		long id = Alice.getId();
		
		IUser newAlice = testee.getUserByID(id);
		assertEquals(newAlice.getLoginName(),"Alice1234");
		assertEquals(newAlice.getDisplayName(),"Alice");
	}
	
	@Test
	public void getUserByIdTestnotFound() {
		BasicExample example = new BasicExample();
		testee.store(example);
		long id = example.getId();
		
		IUser newAlice = testee.getUserByID(id);
		assertEquals(newAlice,null);		
	}
	
	@Test
	public void getLoginNameByIdTest() {
		User Alice = new User("Alice1234","changeMe123","Alice");
		testee.store(Alice);
		IUser newAlice = testee.getUserByLoginName("Alice1234");
		assertEquals(newAlice.getLoginName(),"Alice1234");
		assertEquals(newAlice.getDisplayName(),"Alice");
	}
	
	@Test
	public void isLoginNameTakenTest() {
		User Alice1 = new User("Alice1234","changeMe123","Alice");
		assertFalse(testee.isLoginNameTaken("Alice1234"));
		assertEquals(testee.getAllUsers().size(),0);
		testee.store(Alice1);
		assertTrue(testee.isLoginNameTaken("Alice1234"));
		assertEquals(testee.getAllUsers().size(),1);
		assertEquals(testee.getUserByLoginName("Alice1234").getDisplayName(),"Alice");
	}

	/**
	 * The database still allows adding two User objects 
	 * with the same loginName. 
	 * Checking for taken names is done in WebController
	 */
	@Test
	public void twoUsersWithSameLoginNameTest() {
		User Alice1 = new User("Alice1234","changeMe123","Alice");
		User Alice2 = new User("Alice1234","changeMe555","Bob");
		assertTrue(!testee.isLoginNameTaken("Alice1234"));
		assertEquals(testee.getAllUsers().size(),0);
		testee.store(Alice1);
		assertTrue(testee.isLoginNameTaken("Alice1234"));
	}

	@Test
	public void isCategoryNameTaken() {
		Category cat = new Category("Live",null);
		testee.store(cat);
		assertTrue(testee.isCategoryTitleTaken("Live"));	
		assertTrue(!testee.isCategoryTitleTaken("Live2"));		
	}
	
	@After
	public void cleanup() throws IOException {
		testee.close();

		//Delete the database. We don't need it anymore.
		File f = new File(databaseName);
		if (f.exists())
			f.delete();
	}
}
