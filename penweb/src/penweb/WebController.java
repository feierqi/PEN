/*******************************************************************************
 * Copyright (c) 2012 Team 3: Live Three or Die Hard
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team 3
 *******************************************************************************/

package penweb;

import database.*;
import dataStructure.*;
import exceptions.DuplicateException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * WebController is a class that maintains the interaction between the database and the web view.
 * The methods for creating categories, examples and Users are implemented here.
 * 
 * @author Justin Chines
 * @author Andy Iovanna
 * @author Neil Pomerleau 
 * @author Andy C
 * @author Chrissy
 * @author Mikey
 */
public class WebController {

	private IDatabase db;

	/**
	 * This is the constructor for the WebController class
	 */
	public WebController() {
		this.db = new Db4oDatabase();
	}

	/**
	 * the old embedded mode constructor used for testing purpose only.
	 * 
	 * @param databaseName
	 */
	public WebController(String databaseName) {
		this.db = new Db4oDatabase(databaseName);
	}

	/**
	 * @deprecated
	 * Check if the category name is already taken
	 * Adds a category to the database. A unique ID is assigned within the DB
	 * 
	 * @param name of the desired category
	 * @param desc Description of the desired category
	 */
	public void addCategory(String name, String desc) throws DuplicateException{
		if(!db.isCategoryTitleTaken(name)){
			ICategory cat = new Category(name, desc);
			db.store(cat);
		}
		else{
			throw new DuplicateException("The title of category already exists.");
		}
	}

	/**
	 * add a category to the database.
	 * @param name
	 *            of the category
	 * @param desc
	 *            description of the category
	 * @param owner
	 *            of the category
	 * @param isPublic
	 *            whether or not the category is public Adds a category with the
	 *            given specifications to the database
	 * @return the id of newly added category on success, null otherwise.
	 */
	public Long addCategory(String name, String desc, IUser owner,
			boolean isPublic) {
		ICategory cat = new Category(name, desc);
		cat.assignOwner(owner);
		cat.setPublic(isPublic);
		return db.store(cat);
	}

	/**
	 * get the list of all categories.
	 * @return a list of all categories stored in the database
	 */
	public List<ICategory> getCategories() {
		return db.getAllCategory();
	}

	/**
	 * Get a category by specified id.
	 * This function will return at most one category.
	 * @param id the id if the category
	 * @return the desired category with the given ID if it exists, null otherwise.
	 */
	public ICategory getCategoryById(Long id) {
		List<ICategory> cats = db.getAllCategory();
		for (ICategory c : cats) {
			if (c.getId().equals(id))
				return c;
		}
		return null; // Error statement needs to be added.
	}

	/**
	 * Get a example by specified id.
	 * This function will return at most one example.
	 * @param id the id if the example
	 * @return the desired example with the given ID if it exists, null otherwise.
	 */
	public IExample getExampleById(Long id) {
		List<IExample> ex = db.getAllExample();
		for (IExample e : ex) {
			if (e.getId().equals(id))
				return e;
		}
		return null; // Error statement needs to be added.
	}

	/**
	 * @deprecated
	 * This is the all-important function to add a code example to the database.
	 * 
	 * @param title
	 *            of the code example
	 * @param content
	 *            of the code example. The code itself.
	 * @param language
	 *            So that the user can sort examples by language.
	 * @param loginName
	 *            So that each example is associated with an author with specific loginName
	 * @return the Id of the code example. Each code example has a unique ID to
	 *         help with writing methods in the database and with sorting
	 *         functions.
	 */
	public long addCode(String title, String content, String language, String loginName) {
		// XXX TODO Pass in a username or userId instead of an "author" string.
		IExample ex = new BasicExample();
		ex.setTitle(title);
		ex.setCode(content);
		ex.setLanguage(language);
		// XXX Change the arguments to the login name, password, and displayName
		IUser auth = db.getUserByLoginName(loginName);
		ArrayList<IUser> authors = new ArrayList<IUser>();
		authors.add(auth);
		ex.setAuthors(authors);
		db.store(ex);
		return ex.getId();
	}

	/**
	 * Add a code example to the database.
	 * @param title
	 *            of the code example
	 * @param content
	 *            of the code example. The example itself
	 * @param language
	 *            that the code is written in
	 * @param loginName
	 *            to allow us to specify a user with the code example
	 * @param isPublic
	 *            whether or not the code example should appear as public or
	 *            private
	 * @return the id of the code example added to the database, null if it cannot be added.
	 */
	public Long addCode(String title, String content, String language,
			String loginName, boolean isPublic) {
		// XXX TODO Pass in a username or userId instead of an "author" string.
		IExample ex = new BasicExample();
		ex.setTitle(title);
		ex.setCode(content);
		ex.setLanguage(language);
		ex.setPublic(isPublic);
		// XXX Change the arguments to the login name, password, and displayName
		IUser auth = db.getUserByLoginName(loginName);
		ArrayList<IUser> authors = new ArrayList<IUser>();
		authors.add(auth);
		ex.setAuthors(authors);
		ex.assignOwner(auth);
		return db.store(ex);
	}

	/**
	 * Add a user in the database.
	 * This function will not add user if the login name is taken.
	 * @param loginName login name of the user.
	 * @param password password of the user.
	 * @param displayName display name of the user.
	 * @return true if the user was created successfully, false otherwise.
	 */
	public boolean addUser(String loginName, String password, String displayName) {
		if (db.isLoginNameTaken(loginName))
			return false;

		db.store(new User(loginName, password, displayName));
		return true;
	}

	/**
	 * Attempt to login given a loginName and password.
	 * 
	 * @param loginName
	 *            Name a user should use to login. This is unique across users
	 * @param password
	 *            The password to check
	 * @return True if the user exists and the password was correct, false otherwise.
	 */
	public boolean tryLogin(String loginName, String password) {
		IUser user = db.getUserByLoginName(loginName);

		return ((user != null) && (user.checkPassword(password)));
	}

	/**
	 * Get a list of titles of all code examples in the database.
	 * @return a list of all of the code examples currently in existence
	 */
	public List<String> getTitles() {
		List<IExample> examples = this.db.getAllExample();
		List<String> titles = new ArrayList<String>();
		for (IExample e : examples) {
			titles.add(e.getTitle());
		}
		return titles;
	}

	/**
	 * Get the number of code examples stored in the datbase.
	 * @return the number of entries in the database.
	 */
	public int getNumEntries() {
		List<IExample> examples = this.db.getAllExample();
		return examples.size();
	}

	/**
	 * Get a list of all exmaples in the database.
	 * @return a list of all of the examples in the database
	 */
	public List<IExample> getExamples() {
		return this.db.getAllExample();
	}
	
	/**
	 * Get a User object by login name.
	 * The User object will be returned if there is 
	 * exactly one User object that matches, null otherwise.
	 * @param loginName login name of the user.
	 * @return a User object that matches the given login name.
	 */
	public IUser getUserByLoginName(String loginName) {
		return db.getUserByLoginName(loginName);
	}

	/**
	 * Takes in an entry--IExample, ICategory, User--and adds it to the database.
	 * @param e the entry
	 * @return newly added entry's id
	 */
	public Long store(IEntry e) {
		return db.store(e);
	}

	/**
	 * Closes the database connection
	 */
	public void close() {
		db.close();
	}

	/**
	 * The method checks if the title for the category already exists.
	 * @param name the title of the category to be checked.
	 * @return true if the category title is taken, false otherwise.
	 */
	public boolean isCategoryTitleTaken(String name) {
		return db.isCategoryTitleTaken(name);
	}

	/**
	 * This method returns a list of all code examples accessible by user
	 * and written in the given language 
	 * accessible means written by user or is public.
	 * @param user
	 *            the identified user
	 * @param language
	 *            the identified language
	 * @return List of all code examples written in language by user
	 */
	public List<IExample> getCodeByLanguageAndUser(IUser user, String language) {
		List<IExample> ExamplesByLanguage = db.getByLanguage(language);
		List<IExample> result = new ArrayList<IExample>();

		for (IExample e : ExamplesByLanguage) {
			if (e.isPublic() || (user != null && e.getOwnerId().equals(user.getId()))) {
				result.add(e);
			}
		}
		return result;
	}

	/**
	 * Get all the public examples in the database.
	 * @return All public examples in the database
	 */
	public List<IExample> getAllPublicExamples() {
		List<IExample> getAllExamples = db.getAllExample();
		List<IExample> result = new ArrayList<IExample>();
		for (IExample e : getAllExamples) {
			if (e.isPublic()) {
				result.add(e);
			}
		}
		return result;
	}

	/**
	 * Get the list of languages of examples accessible by the given user.
	 * accessible means written by user or is public.
	 * @param user specified user
	 * @return List of all languages of examples accessible by the given user.
	 */
	public List<String> getLangList(IUser user) {
		List<IExample> examples = db.getAllExample();
		List<String> result = new ArrayList<String>();
		for (IExample e : examples) {
			if (e.isPublic() && !result.contains(e.getLanguage()))
				result.add(e.getLanguage());
			if (user != null)
			{
				if (e.getOwner() != null) {
					if (e.getOwner().equals(user) && !result.contains(e.getLanguage()))
						result.add(e.getLanguage());
				}
			}
		}

		return result;
	}

	/**
	 * read text file and return a list of strings of languages from that file
	 * The file must be named "LangList.txt"
	 * This file must be stored in the same directory as the database file.
	 * This directory should be eclipse directory (run from eclipse) where eclipse.exe is
	 * or in the Tomcat bin directory [TOMCAT HOME]/bin/ 
	 * Note: For embedded version of WebController (for testing), LangList.txt must be in the project directory.
	 * @return a list of strings of languages read from LangList.txt
	 */
	public List<String> readLangListFromFile() {
		BufferedReader in;
		List<String> LangList = new ArrayList<String>();
		try {
			in = new BufferedReader(new FileReader("LangList.txt"));
		}
		catch(IOException e) {
			//System.out.println("file not found");0
			return LangList;
		}
		String lang = "";
		while(lang != null) {
			try {
				lang = in.readLine();
				if(lang!=null && lang.length()!=0)
					LangList.add(lang);
			} catch (IOException e) {
				break;
			}
		}
		return LangList;
	}

	/**
	 * Delete an example or a category. This function will also
	 * - if entry is ICategory, remove all the code examples from the category.
	 * - if entry is IExample, remove itself from any categories and dependencies list.
	 * - if entry is User, do nothing.
	 * @param entry to be deleted
	 * @param user the user that is trying to delete. 
	 * @return 1 if the user is not allowed to delete the given entry. 0 If the
	 *         owner is allowed to delete the entry, in which case it does get
	 *         deleted. 
	 */
	public int delete(IEntry entry, IUser user) {
		//not owner
		if (!entry.getOwner().equals(user))
			return 1;

		//user is owner
		if (entry instanceof ICategory) {
			if(((ICategory) entry).getExampleList().size()!=0)
			{
				ICategory categoryEntry = (ICategory)entry;
				List<IExample> examples = categoryEntry.getExampleList();
				categoryEntry.removeAllExamples();
				for (IExample example : examples) {
					db.store(example);
				}
			}
		}
		else if (entry instanceof IExample) {
			IExample exampleEntry = (IExample) entry;
			if(exampleEntry.getCategories().size()!=0)
			{
				List<ICategory> categories = exampleEntry.getCategories();
				exampleEntry.removeFromAllCategories();
				for (ICategory category : categories) {
					db.store(category);
				}
			}

			if(getDependerOf(exampleEntry).size()!=0)
				removeAllDependency(exampleEntry);
		} 
		else if(entry instanceof IUser) {
			// TODO Allow deletion for users
			//System.out.println("You want to delete a user? Not yet implemented");
		}
		else {
			// TODO ?
			//System.out.println("something else?");
		}
		db.delete(entry);
		return 0;
	}

	/**
	 * Get all examples in database that depend on given example (aka dependers)
	 * @param example the example we want to find what depends on it
	 * @return list of all examples depends on example 
	 */
	public List<IExample> getDependerOf(IExample example) {
		List<IExample> allExamples = db.getAllExample();
		List<IExample> result = new ArrayList<IExample>();
		for(IExample e : allExamples) {
			if(e.getDependency().contains(example)) {
				if(!result.contains(e))
					result.add(e);
			}
		}
		return result;
	}

	/**
	 * Get all the code examples accessible by given user.
	 * accessible means owned by user or us public.
	 * @return a list of all code examples that should be visible to this user
	 */
	public List<IExample> getVisibleExamples(IUser user) {
		List<IExample> results = getAllPublicExamples();

		for (IExample example : db.getAllExample()) {
			if (example.getOwnerId() == user.getId() && !results.contains(example))
				results.add(example);
		}

		return results;
	}

	/**
	 * Get a list of examples that the given user owns
	 * @param user The user to find examples for
	 * @return A list of IExamples the user owns
	 */
	public List<IExample> getOwnedExamples(IUser user) {
		List<IExample> results = new ArrayList<IExample>();

		for (IExample example : db.getAllExample()) {
			if (example.getOwner() == user)
				results.add(example);
		}

		return results;
	}
	/**
	 * For each example in the database, remove the given example from
	 * the dependency list if it depends on the given example.
	 * @param example an example
	 */
	public void removeAllDependency(IExample example){
		for(IExample e : db.getAllExample()){
			if(e.getDependency().contains(example)){
				e.removeDependeny(example);
				db.store(e);
			}
		}
	}

	/**
	 * Checks if an example with the given name already exists
	 * @param name The name of the example
	 * @return True if the name is already being used, false otherwise.
	 */
	public boolean isExampleNameTaken(String name) {
		for (IExample e : db.getAllExample()) {
			if (name.equals(e.getTitle())) return true;
		}

		return false;
	}

	/**
	 * Change text to the escaped form so that it is displayed correctly in web browser.
	 * @param text to be escaped.
	 * @return escaped text.
	 */
	public String escapeHtml(String text) {
		return text.replaceAll("&", "&amp;")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll("\n", "<br>")
				.replaceAll(" ", "&nbsp;")
				.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")
				.replaceAll("\"", "&quot;")
				.replaceAll("'","&#39;");
	}
}
