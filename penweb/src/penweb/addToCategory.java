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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataStructure.*;
import exceptions.*;


/**
 * Servlet implementation class addToCategory
 * @author Andy C
 */
@WebServlet("/addToCategory")
public class addToCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addToCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebController webcon = new WebController();
		String[] cids = request.getParameterValues("cids");
		String eid = request.getParameter("eid");
		IExample ex = webcon.getExampleById(Long.parseLong(eid));
		for (String s : cids) {
			ICategory cat = webcon.getCategoryById(Long.parseLong(s));
			try {
				cat.addCodeExample(ex);
			}
			catch (DuplicateException e){
				// Do nothing for now
			}
			webcon.store(cat);
		}
		webcon.store(ex);
		webcon.close();
		response.sendRedirect("/penweb");
	}

}
