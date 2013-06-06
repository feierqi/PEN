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
 * Superclass for all exception used in PEN project.
 * This class is abstract.
 * @author tpatikorn
 */
public abstract class PENException extends Exception{

	private String message;

	public PENException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}	
	
}
