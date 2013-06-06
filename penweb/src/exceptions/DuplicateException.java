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
 * 
 * This exception is used to check if there will be a duplication
 * when the user tries to add a category or example
 * @author Peng Ren, Thanaporn Patikorn
 *
 */
public class DuplicateException extends PENException{
	
	public DuplicateException(String message) {
		super(message);
	}
}
