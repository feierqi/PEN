package penweb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dataStructure.ICategory;
import dataStructure.IExample;
import dataStructure.IUser;

/**
 * Servlet implementation class deleteExample
 */
@WebServlet("/deleteExample")
public class deleteExample extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteExample() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebController webcon = new WebController();
		HttpSession session = request.getSession(true);
		String loginName = (String) session.getAttribute("name");
		
		// redirect back to index if no loginname was given
		if (loginName == null) {
			response.sendRedirect("/penweb");
			webcon.close();
			return;
		}
		
		// redirect back to index if user does not exist
		IUser user = webcon.getUserByLoginName(loginName);
		if (user == null) {
			response.sendRedirect("/penweb");
			webcon.close();
			return;
		}
		
		Long eid = Long.parseLong(request.getParameter("eid"));
		IExample ex = webcon.getExampleById(eid);
		webcon.delete(ex, user);
		webcon.close();
		response.sendRedirect("/penweb");
	}

}
