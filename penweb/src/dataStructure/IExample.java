/*******************************************************************************
 * Copyright (c) 2012 Team 3: Live Three or Die Hard
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team 3
 *******************************************************************************/

package dataStructure;

import java.util.ArrayList;
import java.util.List;

import exceptions.DuplicateException;

/**
 * IExample is the code entry that a user is allowed to create on the web site.
 * @author tpatikorn
 */
public interface IExample extends IEntry
{
	/**
	 * Adds the example to the given category
	 * @param category The category the code example should be added to
	 * @throws DuplicateException 
	 */
	void addCategory(ICategory category) throws DuplicateException;

	/**
	 * add the given tag to the example
	 * 
	 * @param tag a tag put in example
	 */
	void addTags(String tag);

	/**
	 * getAuthors is a list of authors found from the users created.
	 * @return List of authors
	 */
	List<IUser> getAuthors();

	/**
	 * Get all the categories that the example belongs to 
	 * @return The list of categories that an example belongs to
	 */
	List<ICategory> getCategories();

	/**
	 * Get the code content of the example
	 * @return The code in the example as a String
	 */
	String getCode();

	/**
	 * Gets the language the example was written in
	 * @return language that the example is written in
	 */
	String getLanguage();

	/**
	 * Get the user who owns the example
	 * @return The person who owns this example
	 */
	IUser getOwner();

	/**
	 * A setter function to set the source property of the example
	 * to the given source
	 * @return the source of the example
	 */
	String getSource();

	/**
	 * Get the title of the example
	 * @return The title of an example
	 */
	String getTitle();

	/**
	 * Setter function to set authors for the example
	 * @param a ArrayList<IPerson>: A list of authors
	 */
	void setAuthors(ArrayList<IUser> a);
	/**
	 * A setter function to set the tag property of the example 
	 * to the given categories.
	 * @param categories a list of categories
	 */
	void setCategories(ArrayList<ICategory> categories);

	/**
	 * Get all the ids of categories that the example belongs to
	 * @return a list of ids of category in the categorylist
	 */
	List<Long> getCategoryIds();

	/**
	 * Setter funtion to set code content for the example
	 * @param code The code content to be associated with an example
	 */
	void setCode(String code);
	/**
	 * A setter function to set the language property of the example
	 * to the given language
	 * 
	 * @param language language of the example
	 */
	void setLanguage(String language);

	/**
	 * A setter function to set the source for the example
	 * @param source the source of the example
	 */
	void setSource(String source);

	/**
	 * A setter function to set the title for the example
	 * @param t String: a new title
	 */
	void setTitle(String t);
	
	/**
	 * a setter function to set the tag property of the example to the given tags
	 * 
	 * @param tags a list of tags
	 */
	void setTags(ArrayList<String> tags);

	/**
	 * a getter function to get the tags of the example
	 * 
	 * @return list of tags of example
	 */
	ArrayList<String> getTags();

	/**
	 * Get the authors's names for the example
	 * @return the names of authors for example
	 */
	String getAuthorsNames();
	/**
	 * The getter function to get comment of the example
	 * @return
	 * 		  the comment of the example
	 */
	String getComment();
	/**
	 * The setter function to set the comment for the example
	 */
	void setComment(String comment);
	/**
	 * Add dependencies to the given examples
	 * @param example the example that this depends on
	 */
	void addDependency(IExample example);
	/**
	 * Add dependencies to the given examples
	 * @return list of examples this depends on
	 */
	ArrayList<IExample> getDependency();
	/**
	 * The method is used to remove all the categories from
	 * the category list.
	 */
	void removeFromAllCategories();
	/**
	 * The method is used to remove the given category from the
	 * current categorylist
	 * 
	 * @param category The category that will be removed from
	 * the current category list. 
	 */
	void removeFromCategory(ICategory category);
	/**
	 * Getter method that returns whether or not a given code example is public or not
	 * @return true if the code example is public. False otherwise. 
	 */
	boolean isPublic();

	/**
	 * Sets the isPublic field to the given boolean.
	 * @param changePublic true if the example is to be changed to be public, false otherwise.
	 */
	void setPublic(boolean changePublic);
	/**
	 * remove the given example from the dependency list
	 * @param example
	 *       an example supposed to be removed from the dependency list
	 */
	void removeDependeny(IExample example); 

}
