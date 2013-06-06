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

/**
 * A registered user who owns examples and categories.
 * Contains methods to create a user and modify all of it's fields...
 * for example add a code example, change password, etc.
 * @author tpatikorn
 * @author jfchines
 * @author awiovanna
 * @author kirkgrimsley
 * @author avenkatesh
 */
public class User implements IUser {
	private final String loginName; /* Name used for logging in. This has to be unique. */
	private String displayName; /* Name displayed to others on the site on examples, categories, etc */
	private String password;
	private ArrayList<IExample> ownedExamples;
	private ArrayList<ICategory> ownedCategories;

	/**
	 * A list of examples that the user has edited. This includes any examples
	 * that the user is listed as an "author" for.
	 */
	private ArrayList<IExample> editedExamples;
	private Long id;

	/**
	 * This is the default constructor for the User class.
	 */
	public User(String loginName, String password, String displayName) {
		this.loginName = loginName;
		this.password = password;
		this.displayName = displayName;

		this.ownedExamples = new ArrayList<IExample>();
		this.ownedCategories = new ArrayList<ICategory>();
		this.editedExamples = new ArrayList<IExample>();
		this.id = new Long(-1);
	}

	/**
	 * The getter function to the login name of the user.
	 */
	@Override
	public String getLoginName() {
		return this.loginName;
	}

	/**
	 * The getter function to get the displayname of the user.
	 * @return the displayname of the user. 
	 */
	@Override
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * This method is used to change the display name of the user.
	 */
	@Override
	public void changeDisplayName(String name) {
		this.displayName = name;
	}

	/**
	 * The method is used to change the password for the user.
	 * Before changing the password, the method is going to check
	 * if the user put the old password correctly and enter the 
	 * new password and reenter new password the same.
	 */
	@Override
	public void changePassword(String oldPassword, String newPassword,
			String reenterNew) {
		if (oldPassword.equals(this.password) && newPassword.equals(reenterNew)) {
			this.password = newPassword;
		}
		// needs else case to the effect of
		// System.out.println("Passwords do not match! Password not reset.") or
		// ("Correct password not entered! Password not reset.") case when we
		// know how we're handling it on the front end
	}

	/**
	 * THe method is used to assign an id for the user.
	 * @param id the id intended to assign to the user
	 * @return 1 if the id has already been assigned
	 * 		   0 if the id has been successfully assigned
	 */
	public int assignId(Long id) {
		if (this.id != -1)
			return 1; // return 1 if already assigned
		else
			this.id = id;
		return 0;
	}

	/**
	 * The getter function to get the id of the user.
	 * @return ID of the user
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * The method is used to check if the input password is
	 * the correct password for the user.
	 * @param passwordAttempt the password that is attempted
	 * @return true if the given password is the user's password.
	 */
	@Override
	public Boolean checkPassword(String passwordAttempt) {
		return (passwordAttempt.equals(this.password));
	}

	/**
	 * The method is used to add a new example to the ownership of the user.
	 */
	@Override
	public void addOwnedExample(IExample example) {
		ownedExamples.add(example);
	}

	/**
	 * The getter function to get a list of example owned by the user.
	 * @return list of all code examples owned by the user
	 */
	@Override
	public List<IExample> getOwnedExampleList() {
		return ownedExamples;
	}

	/**
	 * The method is used to add the given category to the category list
	 * owned by the user.
	 * @param category the category intended to give the user
	 */
	@Override
	public void addOwnedCategory(ICategory category) {
		ownedCategories.add(category);
	}

	/**
	 * The getter function to get the list of categories owned by the user.
	 * @return a list of all categories created by the user. 
	 */
	@Override
	public List<ICategory> getOwnedCategoryList() {
		return ownedCategories;
	}

	/**
	 * The method is used add a new example to the list of examples
	 * owned by the user.
	 * @param example a new example intended to give the user
	 */
	@Override
	public void addEditedExample(IExample example) {
		editedExamples.add(example);
	}

	/**
	 * The getter function to get the list of examples edited 
	 * by the user.
	 * @return a list of all code examples edited by user
	 */
	@Override
	public List<IExample> getEditedExampleList() {
		return editedExamples;
	}

	/**
	 * A user does not have an owner.
	 * This function returns null.
	 * Do not use.
	 */
	@Override
	public IUser getOwner() {
		return null;
	}

	/**
	 * A user does not have an owner.
	 * This function returns null.
	 * Do not use.
	 */
	@Override
	public Long getOwnerId() {
		return null;
	}

	/**
	 * A user does not have an owner.
	 * This function returns null.
	 * Do not use.
	 * @param owner do not use
	 */
	@Override
	public int assignOwner(IUser owner) {
		return 0;
	}

	/**
	 * Two users are "equal" if and only if they have the same id.
	 * override the equals function of object class.
	 * @return true if o is an instance of User and
	 *         this and o have the same id, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof IUser) {
			return this.getId().equals(((IUser) o).getId());
		} else
			return false;
	}
}
