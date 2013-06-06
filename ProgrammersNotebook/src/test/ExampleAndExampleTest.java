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

import java.io.File;
import java.util.List;



public class ExampleAndExampleTest {
	private Db4oDatabase db;
	private String databaseName = "ExampleAndCategoryTest.yap";
	
	@Before
	public void setup() {
		// Connect to test database
		db = new Db4oDatabase(databaseName);
	}
	
	
	@Test
	public void testAddExamplePreAssignedID() {
		Category category1 = new Category("Cat", "Cats are nice");
		Category category2 = new Category("Dog", "Dogs are nice");
		BasicExample example1 = new BasicExample();
		example1.setCode("printf(Hello World);");
		example1.setTitle("Hello World");
		BasicExample example2 = new BasicExample();
		example2.setCode("printf(Bye World);");
		example2.setTitle("Bye World");
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
		assertTrue((categoryList.get(0).getId().equals(category1.getId()) && categoryList.get(1).getId().equals(category2.getId()))
				|| (categoryList.get(0).getId().equals(category2.getId()) && categoryList.get(1).getId().equals(category1.getId())));
	}
	
	@Test
	public void testAddExampleUnAssignedIDReStore() {
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
		assertTrue((categoryList.get(0).getId().equals(category1.getId()) && categoryList.get(1).getId().equals(category2.getId()))
				|| (categoryList.get(0).getId().equals(category2.getId()) && categoryList.get(1).getId().equals(category1.getId())));
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
