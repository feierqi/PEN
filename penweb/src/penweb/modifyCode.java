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
import exceptions.DuplicateException;

/**
 * Servlet implementation class modifyCode
 * @author Andy C
 */
@WebServlet("/modifyCode")
public class modifyCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public modifyCode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebController webcon = new WebController();
		Long id = Long.parseLong(request.getParameter("eid"));
		IExample ex = webcon.getExampleById(id);
		String title = request.getParameter("title");		
		
		if (!ex.getTitle().equals(title)) {
			if (webcon.isExampleNameTaken(title)) {
				response.sendRedirect("/penweb/error.jsp?err=7");
				return;
			}
		}
		
		String content = request.getParameter("content");
		String language = request.getParameter("language");
		String comment = request.getParameter("comment");
		String[] cids = request.getParameterValues("cids");
		boolean pub = request.getParameter("public") != null;
		
		ex.setTitle(title);
		ex.setCode(content);
		ex.setLanguage(language);
		ex.setComment(comment);
		ex.setPublic(pub);
		// Note: Not going to implement modifying author, our field will soon
		// be replaced by the new login system
		if (cids != null) {
			for (String s : cids) {
				if (s != null) {
					ICategory cat = webcon.getCategoryById(Long.parseLong(s));
					try {
						cat.addCodeExample(ex);
					}
					catch (DuplicateException e){
						// Do nothing for now
					}
					webcon.store(cat);
				}
			}
		}
		webcon.store(ex);
		response.sendRedirect("/penweb");
		webcon.close();
	}

}
