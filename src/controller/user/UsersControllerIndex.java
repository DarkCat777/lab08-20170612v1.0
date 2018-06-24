package controller.user;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.PMF;
import model.Role;
import model.Users;

@SuppressWarnings("serial")
public class UsersControllerIndex extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "SELECT FROM "+ Users.class.getName();
		List<Users> users = (List<Users>)pm.newQuery(query).execute();
		String query2 = "SELECT FROM "+ Role.class.getName();
		List<Role> roles = (List<Role>)pm.newQuery(query2).execute();
		pm.close();
		req.setAttribute("roles", roles);
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/View/Users/index.jsp").forward(req, resp);
	}
}