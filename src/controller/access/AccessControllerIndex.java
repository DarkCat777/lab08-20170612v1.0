package controller.access;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import controller.PMF;
import model.*;

//envia el paramentro billing a /WEB-INF/Views/Billing/index.jsp
@SuppressWarnings("serial")
public class AccessControllerIndex extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query4 = "SELECT FROM " + Access.class.getName();
		String query5 = "SELECT FROM " + Role.class.getName();
		String query6 = "SELECT FROM " + Resource.class.getName();
		List<Access> access2 = (List<Access>) pm.newQuery(query4).execute();
		List<Role> roles = (List<Role>) pm.newQuery(query5).execute();
		List<Resource> resources = (List<Resource>) pm.newQuery(query6).execute();
		pm.close();
		req.setAttribute("roles", roles);
		req.setAttribute("resources", resources);
		req.setAttribute("access", access2);
		req.getRequestDispatcher("/WEB-INF/View/Access/index.jsp").forward(req, resp);
	}
}
