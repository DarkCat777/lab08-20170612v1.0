package controller.user;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import controller.PMF;
import model.*;

public class UsersControllerView extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user2 = pm.getObjectById(Users.class, Long.parseLong(req.getParameter("id")));
		Role role = null;
		if (user2.getIdRole() != null) {
			role = pm.getObjectById(Role.class, user2.getIdRole());
		}
		pm.close();
		req.setAttribute("user", user2);
		req.setAttribute("role", role);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/View/Users/view.jsp");
		rd.forward(req, resp);
		// req.getRequestDispatcher("/view.jsp").forward(req,
		// resp);
	}
}