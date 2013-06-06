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

import java.io.File;
import java.util.UUID;

import database.Db4oDatabase;

/**
 * Contain non-class specific tests
 * and/or tests that involved many classes
 * @author tpatikorn
 */
public class MiscellaneousTest 
{
	 
	@Before
	public void setup() {
	}
	
	@Test
	public void testUUID() {
		UUID id1 = new UUID(1L,3L);
		UUID id2 = new UUID(1L,3L);
		assertEquals(id1,id2);
	}
	
	@Test
	public void testUUID2() {
		UUID id1 = new UUID(0L,3L);
		UUID id2 = new UUID(2L,3L);
		assertTrue(!id1.equals(id2));
	}
	
	@After
	public void cleanup() {
	}
}
