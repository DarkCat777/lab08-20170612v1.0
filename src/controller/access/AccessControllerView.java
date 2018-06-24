package controller.access;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import controller.PMF;
import model.Access;
import model.Resource;
import model.Role;

public class AccessControllerView extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Access access = pm.getObjectById(Access.class, Long.parseLong(req.getParameter("id")));
		String query2 = "SELECT FROM "+ Role.class.getName();
		String query3 = "SELECT FROM "+ Resource.class.getName();
		List<Role> roles =(List<Role>)pm.newQuery(query2).execute();
		List<Resource> resources =(List<Resource>)pm.newQuery(query3).execute();
		pm.close();
		req.setAttribute("roles", roles);
		req.setAttribute("resources", resources);
		req.setAttribute("access", access);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/View/Access/view.jsp");
		rd.forward(req, resp);
		//req.getRequestDispatcher("/view.jsp").forward(req, resp);
	}
}