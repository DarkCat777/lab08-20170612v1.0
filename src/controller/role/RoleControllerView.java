package controller.role;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import controller.PMF;
import model.Access;
import model.Resource;
import model.Role;
import model.Users;

public class RoleControllerView extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Role role = pm.getObjectById(Role.class, Long.parseLong(req.getParameter("id")));
		pm.close();
		req.setAttribute("role", role);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/View/Role/view.jsp");
		rd.forward(req, resp);
		// req.getRequestDispatcher("/view.jsp").forward(req,
		// resp);
	}
}