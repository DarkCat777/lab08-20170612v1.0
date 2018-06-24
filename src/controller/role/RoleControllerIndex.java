package controller.role;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.PMF;
import model.Role;

@SuppressWarnings("serial")
public class RoleControllerIndex extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "SELECT FROM "+ Role.class.getName();
		List<Role> roles = (List<Role>)pm.newQuery(query).execute();
		pm.close();
		req.setAttribute("roles", roles);
		req.getRequestDispatcher("/WEB-INF/View/Role/index.jsp").forward(req, resp);
	}
}