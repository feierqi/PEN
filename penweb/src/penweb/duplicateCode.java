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

import dataStructure.ICategory;
import dataStructure.IExample;
import dataStructure.IUser;
import exceptions.DuplicateException;

/**
 * Servlet implementation class duplicateCode
 * @author Andy C
 * @author Mikey Boy
 */
@WebServlet("/duplicateCode")
public class duplicateCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public duplicateCode() {
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
		String loginName = request.getParameter("uid");
		IExample ex = webcon.getExampleById(Long.parseLong(eid));
		IUser user = webcon.getUserByLoginName(loginName);
		String title = ex.getTitle();
		String end = "";
		boolean isTaken = true;
		int i = 1;
		while (isTaken) {
			end = " (" + String.valueOf(i) + ")";
			String s = title + end;
			isTaken = webcon.isExampleNameTaken(s);
			i++;
		}
		title += end;
		Long nid = webcon.addCode(title, ex.getCode(), ex.getLanguage(), user.getLoginName(), false);
		IExample nex = webcon.getExampleById(nid);
		if (cids != null) {
			for (String s : cids) {
				ICategory cat = webcon.getCategoryById(Long.parseLong(s));
				try {
					cat.addCodeExample(nex);
				}
				catch (DuplicateException e){
					// Do nothing for now
				}
				webcon.store(cat);
			}
		}
		webcon.store(nex);
		webcon.close();
		response.sendRedirect("/penweb");
	}
}
