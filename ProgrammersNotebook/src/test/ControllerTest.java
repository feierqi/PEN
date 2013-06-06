package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import control.Controller;
import dataStructure.BufferEntry;
import dataStructure.Category;
import dataStructure.IExample;
import database.Db4oDatabase;
import database.IDatabase;
import exceptions.CannotAddToDbException;
import exceptions.PENException;

public class ControllerTest {
	private Controller testee;
	private IDatabase db;
	private String databaseName = "controllerTest.yap";

	@Before
	public void setup() {
		db = new Db4oDatabase(databaseName);
		testee = new Controller(db, null);
	}
	
	@Test
	public void testAddBasicExample() throws PENException {
		BufferEntry buff = new BufferEntry();
		buff.setCode("Hello World");
		buff.setDescription("This statement is false");
		buff.setLanguage("Thai");
		buff.setSource("pikachu.net");
		buff.setTitle("untitled");
		testee.addBasicExample(buff);
		IExample e = db.getByHeader("untitled", null).get(0);
		
		assertEquals(e.getDescription(), "This statement is false");
		assertEquals(e.getSource(), "pikachu.net");
		assertEquals(e.getLanguage(), "Thai");
		assertEquals(e.getCode(), "Hello World");
		
	}

	@Test
	public void testAddCategoryException() {
		BufferEntry b = new BufferEntry();
		b.setTitle("Title");
		b.setDescription("Desc");
		//first time, should be okay
		try
		{
			testee.addCategory(b);
		}
		catch(PENException exception)
		{
			fail("exception thrown on first try");
		}
		
		try
		{
			testee.addCategory(b);
			fail("exception not thrown on second try");
		}
		catch(PENException exception)
		{
			assertEquals(exception.getMessage(),"The title: Title for category is taken.");
			
		}
		
		//TODO: Write test to ensure exception is thrown
	}
	
	@Test
	public void testAddCategorySuccess() throws PENException {
		BufferEntry b = new BufferEntry();
		b.setTitle("Title");
		b.setDescription("Desc");
		testee.addCategory(b);
		
		Category c = (Category) db.getAllCategory().get(0);
		assertEquals(c.getDescription(), "Desc");
		assertEquals(c.getTitle(), "Title");
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
