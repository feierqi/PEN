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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import penweb.WebController;

import dataStructure.BasicExample;
import dataStructure.ICategory;
import dataStructure.IExample;
import dataStructure.IUser;
import database.Db4oDatabase;
import exceptions.DuplicateException;

import penweb.Initializer;

/**
 * Test function in WebController.java
 * @author tpatikorn
 * @author iprangishvili
 * @author dmulcahy 
 */
public class WebControllerTest {
	private WebController testee;
	private Db4oDatabase db;
	private String databaseName = "webConTest.yap";

	@Before
	public void setup() throws IOException {
		testee = new WebController(databaseName);
	}

	@Test
	public void TwoUsernamesTest() {

		boolean add1 = testee.addUser("AliceAndBob", "R4B8!t", "Alice");
		boolean add2 = testee.addUser("AliceAndBob", "W0nD3R", "Bob");

		assertTrue(add1);
		assertFalse(add2);

		testee.close();
		db = new Db4oDatabase(databaseName);

		assertEquals(db.getAllUsers().size(), 1);
		assertEquals(db.getAllUsers().get(0).getDisplayName(), "Alice");

		db.close();
		testee = new WebController(databaseName);
	}

	@Test
	public void addCategoryTest() {
		try{
			testee.addCategory("Alice's", "This is Alice's");
			testee.addCategory("Bob's", "This is Bob's");
		}catch(DuplicateException e){
		}

		List<ICategory> categories = testee.getCategories();

		assertEquals(categories.size(), 2);
		assertNotSame(
				categories.get(0).getTitle().equals("Alice's"),
				categories.get(1).getTitle().equals("Alice's"));
		assertNotSame(
				categories.get(0).getTitle().equals("Bob's"), 
				categories.get(1).getTitle().equals("Bob's"));
		assertNotSame(
				categories.get(0).getDescription().equals("This is Bob's"), 
				categories.get(1).getDescription().equals("This is Bob's"));
		assertNotSame(
				categories.get(0).getDescription().equals("This is Alice's"),
				categories.get(1).getDescription().equals("This is Alice's"));
	}

	@Test
	public void addCategoryTest2() {
		testee.addUser("Alice", "Alice123", "I'm not Alice");
		IUser Alice = testee.getUserByLoginName("Alice");
		testee.addUser("Bob", "Bob123", "I'm Bob");
		IUser Bob = testee.getUserByLoginName("Bob");
		testee.addCategory("Alice's", "This is Alice's", Alice, true);
		testee.addCategory("Bob's", "This is Bob's", Bob, false);

		List<ICategory> categories = testee.getCategories();

		assertEquals(categories.size(), 2);
		ICategory cat0 = null;
		ICategory cat1 = null;
		for (ICategory cat : categories) {
			if (cat.getTitle().equals("Alice's"))
				cat0 = cat;
			if (cat.getTitle().equals("Bob's"))
				cat1 = cat;
		}

		assertTrue(cat0.isPublic());
		assertEquals(cat0.getOwner(), Alice);

		assertFalse(cat1.isPublic());
		assertEquals(cat1.getOwner(), Bob);
	}

	@Test
	public void getCategoryByIdTest() {
		try{
			testee.addCategory("Alice's", "This is Alice's");
			testee.addCategory("Bob's", "This is Bob's");
		}catch(DuplicateException e){
		}

		List<ICategory> categories = testee.getCategories();
		ICategory c1 = categories.get(0);
		ICategory c2 = categories.get(1);

		assertEquals(categories.size(), 2);
		assertNotSame(c1.getTitle().equals("Alice's")
				, c2.getTitle().equals("Alice's"));
		assertNotSame(c1.getTitle().equals("Bob's")
				, c2.getTitle().equals("Bob's"));
		assertNotSame(c1.getDescription().equals("This is Alice's")
				, c2.getDescription().equals("This is Alice's"));
		assertNotSame(c1.getDescription().equals("This is Bob's")
				, c2.getDescription().equals("This is Bob's"));
	}

	@Test
	public void addCodeTest() {
		testee.addCode("AliceAndBob", "R4B8!t", "Alice", null);
		testee.addCode("AliceAndBob", "W0nD3R", "Bob", null);

		List<IExample> examples = testee.getExamples();
		assertEquals(examples.size(), 2);
		assertNotSame(examples.get(0).getCode().equals("R4B8!t")
				, examples.get(1).getCode().equals("R4B8!t"));
		assertNotSame(examples.get(0).getLanguage().equalsIgnoreCase("alice")
				, examples.get(1).getLanguage().equalsIgnoreCase("alice"));
	}

	@Test
	public void addCodeTest2() {
		testee.addUser("Alice", "Alice123", "I'm not Alice");
		IUser Alice = testee.getUserByLoginName("Alice");
		testee.addUser("Bob", "Bob123", "I'm Bob");
		IUser Bob = testee.getUserByLoginName("Bob");

		Long id1 = testee.addCode("AliceAndBob", "R4B8!t", "Java", "Alice",
				true);
		Long id2 = testee.addCode("AliceAndBob", "W0nD3R", "C", "Bob", false);

		List<IExample> examples = testee.getExamples();
		IExample ex0 = null;
		IExample ex1 = null;
		for (IExample ex : examples) {
			if (ex.getOwner().equals(Alice))
				ex0 = ex;
			if (ex.getOwner().equals(Bob))
				ex1 = ex;
		}
		assertTrue(ex0.isPublic());
		assertFalse(ex1.isPublic());
		assertEquals(ex0.getCode(), "R4B8!t");
		assertEquals(ex1.getCode(), "W0nD3R");

	}

	@Test
	public void getCodeByIdTest() {
		testee.addCode("AliceAndBob", "R4B8!t", "Alice", null);
		testee.addCode("AliceAndBob", "W0nD3R", "Bob", null);

		List<IExample> examples = testee.getExamples();
		assertEquals(examples.size(), 2);
		long id1 = examples.get(0).getId();
		long id2 = examples.get(1).getId();

		IExample e1 = testee.getExampleById(id1);
		IExample e2 = testee.getExampleById(id2);

		assertEquals(e1.getTitle(), "AliceAndBob");
		assertEquals(e2.getTitle(), "AliceAndBob");
		assertNotSame(e1.getCode().equals("R4B8!t")
				, e2.getCode().equals("R4B8!t"));
		assertNotSame(e1.getCode().equals("W0nD3R")
				, e2.getCode().equals("W0nD3R"));
		assertNotSame(e1.getLanguage().equalsIgnoreCase("alice")
				, e2.getLanguage().equalsIgnoreCase("alice"));
		assertNotSame(e1.getLanguage().equalsIgnoreCase("bob")
				, e2.getLanguage().equalsIgnoreCase("bob"));
	}

	@Test
	public void getCateogoryByIdTest() {
		testee.addUser("Alice", "Alice123", "I'm not Alice");
		IUser Alice = testee.getUserByLoginName("Alice");
		Long newId = testee.addCategory("Alice's", "This is Alice's", Alice,
				true);

		assertEquals(testee.getCategories().size(), 1);

		assertEquals(testee.getCategoryById(newId),
				testee.getCategories().get(0));
		assertEquals(testee.getCategoryById(newId).getOwner(), Alice);

	}

	@Test
	public void getTitlesTest() {
		testee.addCode("AliceAndBob", "R4B8!t", "Alice", null);
		testee.addCode("AliceAndBob", "W0nD3R", "Bob", null, true);

		assertEquals(testee.getTitles().get(0), "AliceAndBob");
		assertEquals(testee.getTitles().get(1), "AliceAndBob");
		assertEquals(testee.getTitles().size(), 2);
	}

	@Test
	public void getNumEntriesTest() {
		testee.addCode("AliceAndBob", "R4B8!t", "Alice", null);
		testee.addCode("AliceAndBob", "W0nD3R", "Bob", null, true);
		testee.addCode("AliceAndBob", "R4B8!t", "Alice", null);
		testee.addCode("AliceAndBob", "W0nD3R", "Bob", null, true);
		testee.addCode("AliceAndBob", "R4B8!t", "Alice", null);
		testee.addCode("AliceAndBob", "W0nD3R", "Bob", null, true);
		testee.addCode("AliceAndBob", "R4B8!t", "Alice", null);
		testee.addCode("AliceAndBob", "W0nD3R", "Bob", null, true);
		testee.addCode("AliceAndBob", "R4B8!t", "Alice", null);
		testee.addCode("AliceAndBob", "W0nD3R", "Bob", null, true);
		testee.store(new BasicExample());

		assertEquals(testee.getNumEntries(), 11);
	}

	@Test
	public void tryLoginTest() {
		assertFalse(testee.tryLogin("name", "password"));

		testee.addUser("name", "password", "displayName");

		assertTrue(testee.tryLogin("name", "password"));

		assertFalse(testee.tryLogin("name", "password2"));

	}

	@Test
	public void isCateogoryTitleTakenTest() {
		assertFalse(testee.isCategoryTitleTaken("title"));
		testee.addCategory("title", "desc", null, true);
		assertTrue(testee.isCategoryTitleTaken("title"));
	}

	@Test
	public void getCodeByLanguageAndUserTest() {
		testee.addUser("4chaner", "12345", "4chaner");
		testee.addUser("Iva", "password", "Iva");
		testee.addCode("Sleep Sort", "I'm a genius", "C++", "4chaner", true);
		Long id1 = testee.addCode("Sleep Sort", "System.out.printf();", "Java",
				"4chaner", false);
		Long id2 = testee.addCode("Sleep Sort in Java", "I'm a genius", "Java",
				"4chaner", true);
		Long id3 = testee.addCode("Merge Sort", "This is Merge Sort", "Java",
				"Iva", true);
		testee.addCode("simple loop", "while(1){}", "C", "Iva", false);
		Long id4 = testee.addCode("simple loop", "while(true){}", "Java",
				"Iva", false);

		BasicExample ex2 = new BasicExample();
		ex2.assignId(id2);
		BasicExample ex3 = new BasicExample();
		ex3.assignId(id3);
		BasicExample ex4 = new BasicExample();
		ex4.assignId(id4);

		List<IExample> list = new ArrayList<IExample>();
		list.add(ex2);
		list.add(ex3);
		list.add(ex4);

		List<IExample> listExamples = testee.getCodeByLanguageAndUser(
				testee.getUserByLoginName("Iva"), "Java");
		assertEquals(listExamples.size(), 3);
		assertEquals(listExamples.get(0).getLanguage(), "Java");
		assertEquals(listExamples.get(1).getLanguage(), "Java");
		assertEquals(listExamples.get(2).getLanguage(), "Java");
		/* abuse .equals() for testing */
		assertTrue(listExamples.containsAll(list));
		assertTrue(list.containsAll(listExamples));
	}

	@Test
	public void getAllPublicExamplesTest() {
		testee.addUser("4chaner", "12345", "4chaner");
		testee.addUser("Iva", "password", "Iva");
		Long id1 = testee.addCode("Sleep Sort", "I'm a genius", "C++",
				"4chaner", true);
		Long id2 = testee.addCode("Sleep Sort", "System.out.printf();", "Java",
				"4chaner", false);
		Long id3 = testee.addCode("Sleep Sort in Java", "I'm a genius", "Java",
				"4chaner", true);
		Long id4 = testee.addCode("Merge Sort", "This is Merge Sort", "Java",
				"Iva", true);
		Long id5 = testee.addCode("simple loop", "while(1){}", "C", "Iva",
				false);
		Long id6 = testee.addCode("simple loop", "while(true){}", "Java",
				"Iva", false);

		BasicExample ex1 = new BasicExample();
		ex1.assignId(id1);
		BasicExample ex3 = new BasicExample();
		ex3.assignId(id3);
		BasicExample ex4 = new BasicExample();
		ex4.assignId(id4);

		List<IExample> list = new ArrayList<IExample>();
		list.add(ex1);
		list.add(ex3);
		list.add(ex4);

		List<IExample> listExamples = testee.getAllPublicExamples();
		assertTrue(listExamples.containsAll(list));
		assertTrue(list.containsAll(listExamples));
	}

	@Test
	public void getDependerOfTest() {
		testee.addUser("4chaner", "12345", "4chaner");
		testee.addUser("Iva", "password", "Iva");
		Long id1 = testee.addCode("Sleep Sort", "I'm a genius", "C++",
				"4chaner", true);
		Long id2 = testee.addCode("Sleep Sort", "System.out.printf();", "Java",
				"4chaner", false);
		Long id3 = testee.addCode("Sleep Sort in Java", "I'm a genius", "Java",
				"4chaner", true);
		Long id4 = testee.addCode("Merge Sort", "This is Merge Sort", "Java",
				"Iva", true);
		Long id5 = testee.addCode("simple loop", "while(1){}", "C", "Iva",
				false);
		Long id6 = testee.addCode("simple loop", "while(true){}", "Java",
				"Iva", false);
		IExample ex1 = testee.getExampleById(id1);
		IExample ex2 = testee.getExampleById(id2);
		IExample ex4 = testee.getExampleById(id4);
		IExample ex6 = testee.getExampleById(id6);

		ex1.addDependency(ex2);
		ex2.addDependency(ex6);
		ex4.addDependency(ex6);

		testee.store(ex1);
		testee.store(ex2);
		testee.store(ex4);
		testee.store(ex6);

		assertEquals(testee.getDependerOf(ex1).size(), 0);
		assertEquals(testee.getDependerOf(ex2).size(), 1);
		assertEquals(testee.getDependerOf(ex4).size(), 0);
	}

	@Test
	public void getLangListTest() {
		testee.addUser("4chaner", "12345", "4chaner");
		testee.addUser("Iva", "password", "Iva");
		Long id1 = testee.addCode("Sleep Sort", "I'm a genius", "C++",
				"4chaner", true);
		Long id2 = testee.addCode("Sleep Sort", "System.out.printf();", "Java",
				"4chaner", false);
		Long id3 = testee.addCode("Sleep Sort in Java", "I'm a genius", "Java",
				"4chaner", true);
		Long id4 = testee.addCode("Merge Sort", "This is Merge Sort", "Java",
				"Iva", true);
		Long id5 = testee.addCode("simple loop", "while(1){}", "C", "Iva",
				false);
		Long id6 = testee.addCode("simple loop", "while(true){}", "Java",
				"Iva", false);

		List<String> IvaLangList = testee.getLangList(testee
				.getUserByLoginName("Iva"));
		List<String> ChanLangList = testee.getLangList(testee
				.getUserByLoginName("4chaner"));
		List<String> JavaC = new ArrayList<String>();
		List<String> JavaCpp = new ArrayList<String>();

		JavaC.add("Java");
		JavaC.add("C");
		JavaC.add("C++");

		JavaCpp.add("Java");
		JavaCpp.add("C++");

		assertTrue(IvaLangList.containsAll(JavaC));
		assertTrue(ChanLangList.containsAll(JavaCpp));
	}

	@Test
	public void deleteTest() {
		testee.addUser("Iva", "password", "Iva");
		testee.addUser("Alice", "Alice123", "I'm not Alice");
		IUser Alice = testee.getUserByLoginName("Alice");
		IUser Iva = testee.getUserByLoginName("Iva");
		Long id1 = testee.addCode("Sleep Sort", "I'm a genius", "C++", "Alice",
				true);
		Long id2 = testee.addCode("Sleep Sort", "System.out.printf();", "Java",
				"Alice", false);
		Long id3 = testee.addCode("Sleep Sort in Java", "I'm a genius", "Java",
				"Alice", true);
		Long id4 = testee.addCode("Merge Sort", "This is Merge Sort", "Java",
				"Iva", true);
		Long id5 = testee.addCode("simple loop", "while(1){}", "C", "Iva",
				false);
		Long id6 = testee.addCode("simple loop", "while(true){}", "Java",
				"Iva", false);
		Long id7 = testee
				.addCategory("Alice's", "This is Alice's", Alice, true);
		Long id8 = testee.addCategory("Bob's", "This is Bob's", Iva, false);
		IExample ex1 = testee.getExampleById(id1);
		IExample ex2 = testee.getExampleById(id2);
		IExample ex3 = testee.getExampleById(id3);
		IExample ex4 = testee.getExampleById(id4);
		IExample ex5 = testee.getExampleById(id5);
		IExample ex6 = testee.getExampleById(id6);
		ICategory cat1 = testee.getCategoryById(id7);
		ICategory cat2 = testee.getCategoryById(id8);
		try {
			ex1.addCategory(cat1);
		} catch (DuplicateException e) {
			// fail();
		}
		try {
			ex2.addCategory(cat1);
		} catch (DuplicateException e) {
			// fail();
		}
		try {
			ex3.addCategory(cat1);
		} catch (DuplicateException e) {
			// fail();
		}
		try {
			ex4.addCategory(cat2);
		} catch (DuplicateException e) {
			// fail();
		}
		ex2.addDependency(ex1);
		List<IExample> exList = new ArrayList<IExample>();
		exList.add(ex1);
		exList.add(ex2);
		exList.add(ex3);
		exList.add(ex4);
		exList.add(ex5);
		exList.add(ex6);
		List<ICategory> catList = new ArrayList<ICategory>();
		catList.add(cat1);
		catList.add(cat2);

		assertTrue(exList.contains(ex1));
		assertEquals(testee.delete(ex1, Iva), 1);
		assertTrue(testee.getExamples().containsAll(exList));
		//The delete method is changed to delete dependency, so the test cases should be changed
		//assertEquals(testee.delete(ex1, Alice), 2);
		//assertTrue(testee.getExamples().containsAll(exList));
		assertEquals(testee.delete(ex2, Alice), 0);
		exList.remove(ex2);
		assertTrue(testee.getExamples().containsAll(exList));

		assertTrue(testee.getCategories().containsAll(catList));
		assertEquals(testee.delete(cat1, Iva), 1);
		assertTrue(testee.getCategories().containsAll(catList));
		assertEquals(testee.delete(cat1, Alice), 0);
		catList.remove(cat1);
		assertTrue(testee.getCategories().containsAll(catList));

	}
	
	@Test
	public void getVisibleExamplesTest() {
		assertEquals(testee.getVisibleExamples(null).size(),0);
		testee.addUser("user1", null, null);
		testee.addUser("user2", null, null);
		testee.addUser("user3", null, null);
		Long exid11 = testee.addCode(null, null, null, "user1", true);
		Long exid12 = testee.addCode(null, null, null, "user1", false);
		Long exid21 = testee.addCode(null, null, null, "user2", true);
		Long exid22 = testee.addCode(null, null, null, "user2", false);
		IExample emt = new BasicExample();
		emt.assignId(exid11);
		IExample emf = new BasicExample();
		emf.assignId(exid12);
		IExample ent = new BasicExample();
		ent.assignId(exid21);
		IExample enf = new BasicExample();
		enf.assignId(exid22);
		List<IExample> listTrue = new ArrayList<IExample>();
		List<IExample> listMan = new ArrayList<IExample>();
		List<IExample> listNam = new ArrayList<IExample>();
		listTrue.add(emt);
		listTrue.add(ent);
		listMan.add(emt);
		listMan.add(emf);
		listNam.add(ent);
		listNam.add(enf);
		
		IUser user1 = testee.getUserByLoginName("user1");
		IUser user2 = testee.getUserByLoginName("user2");
		IUser user3 = testee.getUserByLoginName("user3");
		
		assertEquals(testee.getOwnedExamples(user1).size(),2);
		assertEquals(testee.getOwnedExamples(user2).size(),2);
		assertEquals(testee.getOwnedExamples(user3).size(),0);
		assertTrue(testee.getOwnedExamples(user1).containsAll(listMan));
		assertTrue(testee.getOwnedExamples(user2).containsAll(listNam));
		
	}

	@Test
	public void getOwnedExamplesTest() {
		assertEquals(testee.getOwnedExamples(null).size(),0);
		testee.addUser("user1", null, null);
		testee.addUser("user2", null, null);
		testee.addUser("user3", null, null);
		Long idManT = testee.addCode(null, null, null, "user1", true);
		Long idManF = testee.addCode(null, null, null, "user1", false);
		Long idNamT = testee.addCode(null, null, null, "user2", true);
		Long idNamF = testee.addCode(null, null, null, "user2", false);
		IExample emt = new BasicExample();
		emt.assignId(idManT);
		IExample emf = new BasicExample();
		emf.assignId(idManF);
		IExample ent = new BasicExample();
		ent.assignId(idNamT);
		IExample enf = new BasicExample();
		enf.assignId(idNamF);
		List<IExample> listTrue = new ArrayList<IExample>();
		List<IExample> listMan = new ArrayList<IExample>();
		List<IExample> listNam = new ArrayList<IExample>();
		listTrue.add(emt);
		listTrue.add(ent);
		listMan.add(emt);
		listMan.add(emf);
		listNam.add(ent);
		listNam.add(enf);
		
		IUser user1 = testee.getUserByLoginName("user1");
		IUser user2 = testee.getUserByLoginName("user2");
		IUser user3 = testee.getUserByLoginName("user3");
		
		assertEquals(testee.getVisibleExamples(user1).size(),3);
		assertEquals(testee.getVisibleExamples(user2).size(),3);
		assertEquals(testee.getVisibleExamples(user3).size(),2);
		assertTrue(testee.getVisibleExamples(user1).containsAll(listMan));
		assertTrue(testee.getVisibleExamples(user1).containsAll(listTrue));
		assertTrue(testee.getVisibleExamples(user2).containsAll(listNam));
		assertTrue(testee.getVisibleExamples(user2).containsAll(listTrue));
		assertTrue(testee.getVisibleExamples(user3).containsAll(listTrue));		
	}
	
	@Test
	public void readLangListFromFileTest() throws IOException {
		List<String> langList = testee.readLangListFromFile();
		assertTrue(langList.contains("python"));
		assertTrue(langList.contains("c"));
		assertTrue(langList.contains("c++"));
		assertTrue(langList.contains("java"));
		assertEquals(langList.size(),6);
		
	}
	
	@After
	public void cleanup() throws IOException {
		testee.close();

		File f1 = new File(databaseName);
		
		if (f1.exists())
			f1.delete();
	}

}
