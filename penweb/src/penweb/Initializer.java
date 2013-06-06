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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletContextEvent;

import com.db4o.ObjectServer;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;

/**
 * Initialization class for the Tomcat server which will start the 
 * db4o server.
 * @author Justin Chines
 * @author Thanaporn Patikorn
 * @author Dennis Koufos
 * @author Iveri Prangishvili
 */
public class Initializer implements javax.servlet.ServletContextListener {
	public static ObjectServer db4oServer;
	private static String databaseName = "penweb3.yap";
	
	/**
	 * Runs when the Tomcat server starts. Starts the db4o server, and generates
	 * a default langlist if one doesn't exist.
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServerConfiguration config = Db4oClientServer.newServerConfiguration();
		config.common().updateDepth(10);
		config.common().activationDepth(10);
		
		db4oServer = Db4oClientServer.openServer(config, databaseName, 0);
		
		File langList = new File("LangList.txt");
		if (!langList.exists()) {
			try {
				FileWriter f = new FileWriter("LangList.txt");
				f.write("C\n" +
						"C++\n" +
						"Java\n" +
						"C#\n" +
						"Python\n" +
						"Ruby\n" +
						"Perl\n" +
						"Lisp\n" +
						"Javascript\n" +
						"PHP\n" +
						"Objective-C\n" +
						"Lua\n" +
						"Assembly\n" +
						"Basic\n" +
						"Pascal\n" +
						"XML\n" +
						"HTML\n" +
						"CSS\n" +
						"SQL\n");
				f.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Runs when the Tomcat server ends.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		db4oServer.close();		
	}
	
	/**
	 * get the name of the database file
	 * @return the name of the database file
	 */
	public static String getDatabaseName() {
		return databaseName;
	}
}