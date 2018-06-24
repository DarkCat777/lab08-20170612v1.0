package controller.access;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import controller.PMF;
import model.*;
//envia el paramentro billing a /WEB-INF/Views/Billing/index.jsp
@SuppressWarnings("serial")
public class AccessControllerIndex  extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "SELECT FROM "+ Access.class.getName();
		String query2 = "SELECT FROM "+ Role.class.getName();
		String query3 = "SELECT FROM "+ Resource.class.getName();
		List<Access> access = (List<Access>)pm.newQuery(query).execute();
		List<Role> roles =(List<Role>)pm.newQuery(query2).execute();
		List<Resource> resources =(List<Resource>)pm.newQuery(query3).execute();
		pm.close();
		req.setAttribute("roles", roles);
		req.setAttribute("resources", resources);
		req.setAttribute("access", access);
		req.getRequestDispatcher("/WEB-INF/View/Access/index.jsp").forward(req, resp);
	}
}
