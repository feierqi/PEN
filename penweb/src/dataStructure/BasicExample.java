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
 * The Basic Examples class uses the parameters that describe a code example to create an example. 
 * Also the dependencies, categories, users and other properties are added to the example.
 * @author tpatikorn 
 */
public class BasicExample implements IExample {
	/**
	 * A list of authors of an example
	 */
	private ArrayList<IUser> authors;

	/**
	 * Categories the example belongs to
	 */
	private ArrayList<ICategory> categoryList = new ArrayList<ICategory>();

	/**
	 * The code for the example
	 */
	private String code;

	/**
	 * An ID for the example in the database
	 */
	private Long id;

	/**
	 * List of examples this example depends on
	 */
	private ArrayList<IExample> dependency = new ArrayList<IExample>();

	/**
	 * Boolean that shows whether an example is public
	 */
	private boolean publicEx;

	/**
	 * Language in which the example is written
	 */
	private String language;
	private IUser owner;

	/**
	 * Where is the source of the example
	 */
	private String source;

	/**
	 * all the tags include in the example
	 */
	private ArrayList<String> tags = new ArrayList<String>();

	/**
	 * Title of the example
	 */
	private String title;

	/**
	 * A comment to describe an example or a change in an example
	 */
	private String comment;

	/**
	 * BasicExample assigns the authors to a list of users. The owner by default is no one.
	 */
	public BasicExample() {
		authors = new ArrayList<IUser>();
		owner = null;
		this.id = -1L;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<IExample> getDependency() {
		return dependency;
	}

	/**
	 * {@inheritDoc}
	 */
	// Modified by Peng Ren to check the duplication of the example and
	// throw an exception
	@Override
	public void addCategory(ICategory category) throws DuplicateException {
		if (!this.isInCategory(category)) {
			categoryList.add(category);
			if(!category.getExampleList().contains(this))
				category.addCodeExample(this);
		}
		else {
			throw new DuplicateException("This example is already in category:"+category.getTitle());
		}
	}
	
	@Override
	public int assignId(Long id) {
		if (this.id != -1)
			return 1; // return 1 if already assigned
		else
			this.id = id;
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int assignOwner(IUser owner) {
		if (this.owner != null)
			return 1; // return 1 if already assigned
		else
			this.owner = owner;
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IUser> getAuthors() {
		return authors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAuthorsNames() {
		String nameList = "";

		for (int i = 0; i < authors.size(); ++i) {
			nameList += authors.get(i).getDisplayName();
			if (i < authors.size() - 1)
				nameList += ", ";
		}

		return nameList;
	}

	/**
	 * Gets the list of categories the example belongs to.
	 * @return LinkedList<String>
	 */
	@Override
	public List<ICategory> getCategories() {
		return categoryList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLanguage() {
		return this.language.trim();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IUser getOwner() {
		return owner;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getOwnerId() {
		//XXX This should never be false
		if (this.owner != null)
			return this.owner.getId();
		return (long) 0;
	}
	
	/**
	 * Gets the source of an example
	 * @return String
	 */
	@Override
	public String getSource() {
		return this.source;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<String> getTags() {
		return this.tags;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * helper function check if the example is already in category.
	 * @param category
	 *            the category wanted to be check
	 * @return true if the example is in category. false otherwise
	 */
	private boolean isInCategory(ICategory category) {
		return this.categoryList.contains(category);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAuthors(ArrayList<IUser> authors) {
		this.authors = authors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCategories(ArrayList<ICategory> categories) {
		this.categoryList = categories;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * The method used to add a new tag to the current tag list.
	 * (Feature of checking existence of tag in tags should be
	 * added later)
	 */
	@Override
	public void addTags(String tag) {
		// @TODO check existence of tag in tags
		this.tags.add(tag);

	}

	/**
	 * The method is used to clone an already created example.
	 * The cloned example  will have all the features the same
	 * as the original one except the id.
	 * 
	 * @return a new example with same features as the original one
	 * except the id
	 */
	@Override
	public BasicExample clone() {
		BasicExample clone = new BasicExample();

		clone.authors = (ArrayList<IUser>) this.authors.clone();
		clone.categoryList = (ArrayList<ICategory>) this.categoryList.clone();
		clone.code = new String(this.code);
		clone.dependency = (ArrayList<IExample>) this.dependency.clone();
		clone.title = new String(this.title);
		clone.language = new String(this.language);
		clone.source = new String(this.source);
		clone.tags = (ArrayList<String>) this.tags.clone();

		return clone;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Long> getCategoryIds() {
		List<Long> ids = new ArrayList<Long>();
		for (ICategory category : categoryList)
			ids.add(category.getId());
		return ids;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addDependency(IExample example) {
		boolean contained = false;
		if(dependency.size() != 0){
			for(int i = 0; i < dependency.size(); i++){
				if(example == dependency.get(i)){
					contained = true;
				}
			}
			if(!contained){
				dependency.add(example);
			}
		}
		else{
			dependency.add(example);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getComment() {
		return comment;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeFromCategory(ICategory category) {
		for (int i = this.categoryList.size() - 1; i >= 0; i--) {
			ICategory cat = this.categoryList.get(i);
			if (cat.getId().equals(category.getId()))
				this.categoryList.remove(cat);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeFromAllCategories() {
		for (ICategory cat : this.categoryList) {
			cat.removeExample(this);
		}
		this.categoryList = new ArrayList<ICategory>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPublic() {
		return this.publicEx;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPublic(boolean changePublic) {
		this.publicEx = changePublic;
	}

	/**
	 * Two examples are "equal" if and only if they have the same id.
	 * override the equals function of object class.
	 * @return true if o is an instance of IExample and
	 *         this and o have the same id, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof IExample) {
			return this.getId().equals(((IExample) o).getId());
		} else
			return false;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDependeny(IExample example){
		if(dependency.contains(example)){
			for(int i = 0; i < dependency.size(); i++){
				if(example == dependency.get(i)){
					dependency.remove(i);
				}
			}
		}
	}
}
