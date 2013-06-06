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

/**
 * IEntry should be an entry (example, category, user)
 * @author tpatikorn
 */
public interface IEntry 
{	
	/**
	 * Get the owner of the entry.
	 * @return The owner of an entry.
	 */
	IUser getOwner();
	
	/**
	 * Get the id of any entry
	 * @return an entry's id of long
	 */
	Long getId();
	
	/**
	 * Get the user's id
	 * @return an user's id of long
	 */
	Long getOwnerId();
	
	/**
	 * Assigns an owner for an entry. It will do nothing if the entry already has an owner
	 * @param owner The person who will own the entry
	 * @return 0 if success, 1 if entry already has an owner
	 */
	int assignOwner(IUser owner);
	
	/**
	 * Assigns an id for an entry. It will do nothing if the entry already has an id
	 * @param id id to be assigned
	 * @return 0 if success, 1 if entry already has an id
	 */
	int assignId(Long id);
}
