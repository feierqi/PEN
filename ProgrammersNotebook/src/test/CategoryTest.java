package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataStructure.BasicExample;
import dataStructure.Category;
import dataStructure.IExample;
import database.Db4oDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList; 
import java.util.List;


public class CategoryTest {
	private Category testee;
	private Db4oDatabase db;
	
	@Before
	public void setup() {
		// Connect to test database
		db = new Db4oDatabase("CategoryTest.yap");
		
		testee = new Category(null, null);
	}
	
	@Test
	public void testAddCodeExample() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		BasicExample example = new BasicExample();
		
		// Attempt to add the same example twice
		testee.addCodeExample(example);
		testee.addCodeExample(example);
		
		Field field = Category.class.getDeclaredField("exampleList");
		field.setAccessible(true);
		
		// The example should have been added only once
		assertEquals(((List)field.get(testee)).size(), 1);
	}
	
	@After
	public void cleanup() {
		db.close();
	}
}
