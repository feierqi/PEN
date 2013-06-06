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

import java.util.List;

import exceptions.DuplicateException;

/**
 * An interface for a category, in case there are more category types.
 * @author tpatikorn
 */

/**
 * The ICategory interface is used to connect the web view with the database for categories.
 */
public interface ICategory extends IEntry{
	
	/**
	 * Get the tile of a category
	 * @return Returns the title of the category. For example: "Web" or "Mobile".
	 */
	String getTitle();
	
	/**
	 * Sets the title of a category.
	 * @param title The title of the category
	 */
	void setTitle(String title);
	
	/**
	 * Get the description of the category
	 * @return Returns the description of a category.
	 */
	String getDescription();
	
	/**
	 * Sets a description of a category.
	 * @param description The new description
	 */
	void setDescription(String description);
	
	/**
	 * Adds a new example to this category.
	 * @param example The example entry to add.
	 * @throws DuplicateException 
	 */
	void addCodeExample(IExample example) throws DuplicateException;

	/**
	 * The getter function to get the example list.
	 * @return The list of examples in the category.
	 */
	List<IExample> getExampleList();
	
	/**
	 * The method is used to get a list of ids of the examples
	 * of the category.
	 * 
	 * @return A list of ids of the examples of the category
	 */
	List<Long> getExampleIds();

	/**
	 * Remove the given example from the category.
	 * 
	 * @param example The example to be removed from the category
	 */
	void removeExample(IExample example);

	/**
	 * The method is used to remove all the examples from the category.
	 */
	void removeAllExamples();
	
	/**
	 * The method is used to show if the category is public.
	 * 
	 * @return True if the category is public
	 */
	public boolean isPublic();

	/**
	 * The method is used to set the public feature of the category.
	 * 
	 * @param isPublic The boolean value is going to set for isPublic
	 */
	public void setPublic(boolean isPublic);
	
	/**
	 * The method is used to get all the public examples in the category.
	 * 
	 * @return The list of examples in the category that are public
	 */
	List<IExample> getPublicExamples();
	
	/**
	 * The method is used to get a list of examples that the user is 
	 * legally to see.
	 * 
	 * @param user The current user using the method
	 * @return A list of unique examples that the user has the right to see
	 */
	List<IExample> getVisibleExamples(IUser user);
	
	/**
	 * Gets a list of examples in the category that the given user owns
	 * @param user The user to get owned examples for
	 * @return A list of IExamples
	 */
	List<IExample> getOwnedExamples(IUser user);
}
