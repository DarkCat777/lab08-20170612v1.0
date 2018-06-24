package controller.user;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import controller.PMF;
import model.*;

public class UsersControllerView extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user = pm.getObjectById(Users.class, Long.parseLong(req.getParameter("id")));
		Role role=null;
		if(user.getIdRole()!=null){
			role = pm.getObjectById(Role.class, user.getIdRole());
		}
		pm.close();
		req.setAttribute("user", user);
		req.setAttribute("role", role);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/View/Users/view.jsp");
		rd.forward(req, resp);
		//req.getRequestDispatcher("/view.jsp").forward(req, resp);
	}
}