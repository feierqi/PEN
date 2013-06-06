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
		NonUser u = new NonUser("anAuthor");
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
		NonUser u = new NonUser("anAuthor");
		entry.assignOwner(u);
		testee.store(entry);
		String name = testee.getByHeader(null, u).get(0).getOwner().getName();
		assertEquals(name, entry.getOwner().getName());
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
