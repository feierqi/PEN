package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dataStructure.BasicExample;
import dataStructure.Category;

public class BasicExampleTest {
	private BasicExample testee;
	
	@Before
	public void setup() {
		testee = new BasicExample();
	}

	@Test
	public void testAddCategory() {
		Category category = new Category(null, null);
		
		// Attempt to add the same category twice
		testee.addCategory(category);
		testee.addCategory(category);
		
		// The category should have been added only once
		assertEquals(testee.getCategories().size(), 1);
	}

}
