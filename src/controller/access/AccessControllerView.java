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
		User user = UserServiceFactory.getUserService().getCurrentUser();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if (user == null) {
			req.getRequestDispatcher("/user/login").forward(req, resp);
		} else {
			String query = "SELECT FROM " + Users.class.getName() + " WHERE email=='" + user.getEmail()
					+ "' && status==true";
			List<Users> users = (List<Users>) pm.newQuery(query).execute();
			if (users.isEmpty()) {
				// ERROR NO EXISTE UN USUARIO O NO ESTA ACTIVO
				String codigoError = "ERROR NO ES UN USUARIO REGRISTRADO O NO ESTA ACTIVO";
				req.setAttribute("error", codigoError);
				req.getRequestDispatcher("/WEB-INF/View/Users/error.jsp").forward(req, resp);
			} else {
				String query2 = "SELECT FROM " + Resource.class.getName() + " WHERE url=='" + req.getServletPath()
						+ "' && status==true";
				List<Resource> resource = (List<Resource>) pm.newQuery(query2).execute();
				if (resource.isEmpty()) {
					// ERROR NO EXISTE UN RECURSO O NO ESTA ACTIVO
					String codigoError = "ERROR NO EXISTE UN RECURSO O NO ESTA ACTIVO";
					req.setAttribute("error", codigoError);
					req.getRequestDispatcher("/WEB-INF/View/Users/error.jsp").forward(req, resp);
				} else {
					String query3 = "SELECT FROM " + Access.class.getName() + " WHERE idRole=="
							+ users.get(0).getIdRole() + " && idUrl==" + resource.get(0).getId() + " && status==true";
					List<Access> access = (List<Access>) pm.newQuery(query3).execute();
					if (access.isEmpty()) {
						// ERROR NO EXISTE UN ACCESO O NO ESTA ACTIVO
						String codigoError = "ERROR NO EXISTE UN ACCESO O NO ESTA ACTIVO";
						req.setAttribute("error", codigoError);
						req.getRequestDispatcher("/WEB-INF/View/Users/error.jsp").forward(req, resp);
					} else {
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
			}
		}

	}
}