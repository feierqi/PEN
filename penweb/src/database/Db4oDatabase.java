/*******************************************************************************
 * Copyright (c) 2012 Team 3: Live Three or Die Hard
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team 3
 *******************************************************************************/

package database;

import java.util.ArrayList;
import java.util.List;

import penweb.Initializer;

import com.db4o.*;
import com.db4o.config.ClientServerConfiguration;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;
import com.db4o.query.*;

import dataStructure.ICategory;
import dataStructure.IEntry;
import dataStructure.IExample;
import exceptions.NoIdAvailableException;
import dataStructure.IUser;

/*
 * How to create a new instance of this database using IDatabase:
 * IDatabase db = new Db4oDatabase("db/localDb.yap");
 * 
 * Now you can store and query
 * db.store(anEntry);
 * List<IEntry> entries = db.getAll();
 * 
 * If you have any questions about how to use this ask Andy C <andy@wpi.edu>
 */

/** 
 * This class acts as an interface to a DB4O database. It
 * connects to a server running locally upon creation. There are 
 * methods to retrieve data from the database.
 * @author Andy Creeth
 * @author Justin Chines
 * @author tpatikorn
 * @author awiovanna
 */
public class Db4oDatabase implements IDatabase {
	final private long maxID = 100000000000L;
	private ObjectContainer db;
	private ObjectServer server;

	/**
	 * Default constructor. This constructor uses client-server model.	
	 */
	public Db4oDatabase() {
		db = Initializer.db4oServer.openClient();
	}

	/**
	 * For testing purpose only
	 * @param path
	 */
	public Db4oDatabase(String path) {
		EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
		configuration.common().updateDepth(10);
		configuration.common().activationDepth(10);

		db = Db4oEmbedded.openFile(configuration, path);
	}

	/**
	 * for storing purposes
	 * store an entry in db4odatabase
	 * return null if the entry cannot be stored (no id available)
	 */
	@Override
	public Long store(IEntry e) {
		//e.assignOwner(null);
		try {
			Long newId;
			newId = this.generateEntryId();
			e.assignId(newId);
			db.store(e);
			return newId;
		} catch (NoIdAvailableException e1) {
			return null;
		}
	}

	/**
	 * This method is used to get all the entries in the database
	 * This method should not be used.
	 * @return a list of all entries in database
	 */
	@Override
	public List<IEntry> getAll() {
		return db.query(IEntry.class);
	}

	/**
	 * This method is used to get an example from
	 * the database by the given title and user name.
	 * 
	 * @param title
	 * 				title string input
	 * @param owner
	 * 				owner of the example
	 * 
	 * @return all the examples with the title name and owned
	 * by the given user if both the title and owner exist
	 * all the examples with the title name if the title is given but
	 * the user is not given
	 * all the exmaples owned by the user if the title is not given but
	 * the user is provided
	 * all the examples if both title and user are not provided
	 * 
	 */
	@Override
	public List<IExample> getByHeader(final String title, final IUser owner) {

		boolean hasTitle = (title != null);
		boolean hasOwner = (owner != null);

		// All authors searches only work if lists are ordered the same.
		if (hasTitle && hasOwner) {
			return db.query(new Predicate<IExample>() {
				public boolean match(IExample e) {
					return (e.getTitle().equals(title) && e.getOwnerId() == owner
							.getId());
				}
			});
		} else if (hasTitle) {
			return db.query(new Predicate<IExample>() {
				public boolean match(IExample e) {
					return e.getTitle().equals(title);
				}
			});
		} else if (hasOwner) {
			return db.query(new Predicate<IExample>() {
				public boolean match(IExample e) {
					if (e.getOwner() != null)
						return e.getOwner().getLoginName()
								.equals(owner.getLoginName());
					else
						return (e.getOwner() == owner);
				}
			});
		} else
			return db.query(IExample.class);
	}

	/**
	 * A cool function would be implemented later to get
	 * examples by keyword.
	 */
	@Override
	public List<IExample> getByKeyword(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Delete an entry from the dataabase
	 * @param e an entry to be deleted
	 */
	@Override
	public void delete(IEntry e) {
		db.delete(e);
	}

	/**
	 * close the da4odatabase
	 */
	@Override
	public void close() {
		db.close();
	}

	/**
	 * Get all the examples in the database
	 * @return A list of all examples in the database
	 */
	public List<IExample> getAllExample() {
		return db.query(IExample.class);
	}

	/**
	 * Get all the Categroies in the database
	 * @return A list of all categories in the database
	 */
	public List<ICategory> getAllCategory() {
		return db.query(ICategory.class);
	}

	/**
	 * Get all the users in the database
	 * @return A list of all users in the database
	 */
	public List<IUser> getAllUsers() {
		return db.query(IUser.class);
	}

	/**
	 * Get the all the names of the all the categories in the database
	 * @return A list of all category names.
	 */
	public ArrayList<String> listCategoryNames() {
		List<ICategory> catList = getAllCategory();
		ArrayList<String> nameList = new ArrayList<String>();
		for (int i = 0; i < catList.size(); i++) {
			nameList.add(catList.get(i).getTitle());
		}
		return nameList;
	}

	/**
	 * Get all the user names in the database
	 * @return A list of strings containing all user login names in the database
	 */
	public ArrayList<String> listUserLoginNames() {
		List<IUser> userList = getAllUsers();
		ArrayList<String> loginNameList = new ArrayList<String>();

		for (IUser user : userList) {
			loginNameList.add(user.getLoginName());
		}

		return loginNameList;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isLoginNameTaken(String loginName) {
		return listUserLoginNames().contains(loginName);
	}

	/**
	 * search for an entry by its id
	 * @param id the id of the entry
	 * @return the entry with the given id if there is
	 * the unique entry exists
	 * 			null if there is no entry with the given id
	 * 			null if there are more than one entries with
	 * the given id(should throw an exception here later)
	 */
	@Override
	public IEntry getByID(final Long id) {
		List<IEntry> list = db.query(new Predicate<IEntry>() {
			public boolean match(IEntry e) {
				Long thisid = e.getId();
				return (thisid.equals(id));
			}
		});
		if (list.size() == 1)
			return list.get(0);
		else if (list.size() == 0)
			return null;
		else
			return null; // throw non-unique exception if there is more than one
		// result
	}

	/**
	 * The method is used to produce an id for an entry.
	 * @return a new id
	 */
	@Override
	public Long getNewId() {
		return (long) (Math.random() * 100000000);
	}

	/**
	 * search for a category by its id
	 * @param id the id of the category
	 * @return the category with the given id if there is
	 * the unique category exists
	 * 			null if there is no category with the given id
	 * 			null if there are more than one categories with
	 * the given id(should throw an exception here later)
	 */
	@Override
	public ICategory getCategoryByID(final Long id) {
		List<ICategory> list = db.query(new Predicate<ICategory>() {
			public boolean match(ICategory e) {
				Long thisid = e.getId();
				return (thisid.equals(id));
			}
		});
		if (list.size() == 1)
			return list.get(0);
		else if (list.size() == 0)
			return null;
		else
			return null; // throw non-unique exception if there is more than one
		// result
	}
	
	/**
	 * Get the code example that has the given id.
	 * This function will return IExample object with specified id 
	 * if exactly one example has that id, null otherwise.
	 * @param id the id of the example
	 * @return the example with the given id if exactly one example matches,
	 *         null otherwise.
	 */
	@Override
	public IExample getExampleByID(final Long id) {
		List<IExample> list = db.query(new Predicate<IExample>() {
			public boolean match(IExample e) {
				Long thisid = e.getId();
				return (thisid.equals(id));
			}
		});
		if (list.size() == 1)
			return list.get(0);
		else if (list.size() == 0)
			return null;
		else
			return null; // throw non-unique exception if there is more than one
		// result
	}

	/**This method is used to get the specific user by the 
	 * given login name
	 * @param loginName the login name of the user
	 * @return the user with the given login name if there is
	 * the unique login name exists
	 * 			null if there is no user with the given login name
	 * 			null if there are more than one users with
	 * the given login name(should throw an exception here later)
	 */
	@Override
	public IUser getUserByLoginName(final String loginName) {
		List<IUser> results = db.query(new Predicate<IUser>() {
			public boolean match(IUser e) {
				return (e.getLoginName().equals(loginName));
			}
		});

		if (results.size() == 1)
			return results.get(0);
		else if (results.size() == 0)
			return null;
		else
			return null; // Throw an exception because two users have the same
							// login name.
	}
	
	/**
	 * Get the user that has the given id.
	 * This function will return User object with specified id 
	 * if exactly one user has that id, null otherwise.
	 * @param id the id of the user
	 * @return the user with the given id if exactly one user matches,
	 *         null otherwise.
	 */
	@Override
	public IUser getUserByID(final Long id) {
		List<IUser> results = db.query(new Predicate<IUser>() {
			public boolean match(IUser e) {
				return (e.getId().equals(id));
			}
		});

		if (results.size() == 1)
			return results.get(0);
		else if (results.size() == 0)
			return null;
		else
			return null; // Throw an exception because two users have the same
							// login name.
	}


	/**
	 * The method generates new id for a new entry.
	 * The method first tries to produce a random id for the new entry 
	 * and then check the availability for the new id.
	 * 
	 * @return a new id if the id is available
	 * 			thrown a NoIdAvailableException if there is no id below maxID available
	 * @throws NoIdAvailableException
	 */
	@Override
	public Long generateEntryId() throws NoIdAvailableException {
		// try to generate random number first
		for (long i = 0; i < maxID; i++) {
			long newId = (long) (Math.random() * maxID);
			if (this.getByID(newId) == null)
				return newId;
		}
		// if no ID available after randomizing maxID times, loop through to
		// check
		for (long newId = 0; newId < maxID; newId++) {
			if (this.getByID(newId) == null)
				return newId;
		}
		throw (new NoIdAvailableException(maxID, "MaxID reached"));
	}

	/**
	 * The method checks if the title for the category already exists.
	 * @return	true if there is already the name for a category
	 * 			false if the name is not used
	 */
	@Override
	public boolean isCategoryTitleTaken(String name) {
		List<ICategory> catlist = this.getAllCategory();
		for (ICategory cat : catlist) {
			if (name.equals(cat.getTitle()))
				return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IExample> getExampleByUser(final IUser user) {
		List<IExample> list = db.query(new Predicate<IExample>() {
			public boolean match(IExample e) {
				IUser thisOwner = e.getOwner();
				return (thisOwner.equals(user));
			}
		});
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IExample> getByLanguage(final String lang) {
		List<IExample> list = db.query(new Predicate<IExample>() {
			public boolean match(IExample e) {
				String thisLanguage = e.getLanguage();
				return (thisLanguage.equalsIgnoreCase(lang)); 
			}
		});
		return list;
	}

	/**This method is used to get all the examples owned by the given user
	 * @param user specified user
	 * @return a list of all categories that the user has created.
	 */
	public List<ICategory> getCategoryByUser(final IUser user) {
		List<ICategory> list = db.query(new Predicate<ICategory>() {
			public boolean match(ICategory e) {
				IUser thisOwner = e.getOwner();
				return (thisOwner.equals(user));
			}
		});
		return list;
	}
}
