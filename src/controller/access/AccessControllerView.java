package controller.access;

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

public class AccessControllerView extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Access access2 = pm.getObjectById(Access.class, Long.parseLong(req.getParameter("id")));
		String query4 = "SELECT FROM " + Role.class.getName();
		String query5 = "SELECT FROM " + Resource.class.getName();
		List<Role> roles = (List<Role>) pm.newQuery(query4).execute();
		List<Resource> resources = (List<Resource>) pm.newQuery(query5).execute();
		pm.close();
		req.setAttribute("roles", roles);
		req.setAttribute("resources", resources);
		req.setAttribute("access", access2);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/View/Access/view.jsp");
		rd.forward(req, resp);
		// req.getRequestDispatcher("/view.jsp").forward(req, resp);
	}
}