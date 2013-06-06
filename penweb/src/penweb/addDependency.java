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

/**
 * Servlet implementation class addDependency
 * @author Mikey Boy
 */
@WebServlet("/addDependency")
public class addDependency extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addDependency() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebController webcon = new WebController();
		String eid = request.getParameter("eid");
		IExample ex = webcon.getExampleById(Long.parseLong(eid));
		String did = request.getParameter("did");
		IExample dx = webcon.getExampleById(Long.parseLong(did));
		if (dx.equals(null)) {
			response.sendRedirect("/penweb");
			return;
		}
		ex.addDependency(dx);
		webcon.store(ex);
		webcon.close();
		response.sendRedirect("edit.jsp?id="+eid);
	}
}
