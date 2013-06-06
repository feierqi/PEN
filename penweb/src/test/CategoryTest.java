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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataStructure.BasicExample;
import dataStructure.Category;
import database.Db4oDatabase;
import exceptions.DuplicateException;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Test Category-related functions
 * Except BasicExample-Category relationship
 * @author tpatikorn
 * @author iprangishvili
 * @author dmulcahy
 * 
 */
public class CategoryTest {
	private Db4oDatabase db;
	
	@Before
	public void setup() {
		db = new Db4oDatabase("CategoryTest.yap");
	}
	
	@Test
	public void testAddCodeExample() throws DuplicateException{
		Category category = new Category("Test","This is test");
		BasicExample example = new BasicExample();
		
		// Attempt to add the same example twice
		category.addCodeExample(example);
		try {
			category.addCodeExample(example);
			fail();
		} catch (DuplicateException e) {
			//it's supposed to come here
		}
		
		// The example should have been added only once
		assertEquals(category.getExampleList().size(), 1);
	}
	
	@After
	public void cleanup() {
		db.close();
	}
}
