/*******************************************************************************
 * Copyright (c) 2012 Team 3: Live Three or Die Hard
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team 3
 *******************************************************************************/

package exceptions;

/**
 * This exception is thrown when there is no unique id, less than maxID, available for new object.
 * @author tpatikorn
 */
public class NoIdAvailableException extends PENException {

	/**
	 * the maximum id of the current implementation.
	 */
	public final long maxID;
	
	/**
	 * @param maxID is the max value of ID database will assign to object
	 * @param message additional information about the error (apart from maxID)
	 */
	public NoIdAvailableException(long maxID, String message) {
		super(message);
		this.maxID = maxID;
	}

}
