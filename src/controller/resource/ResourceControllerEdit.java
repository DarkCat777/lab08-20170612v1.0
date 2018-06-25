package controller.resource;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import controller.PMF;
import model.Access;
import model.Resource;
import model.Role;
import model.Users;

@SuppressWarnings("serial")
public class ResourceControllerEdit extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
						Resource resource2 = pm.getObjectById(Resource.class, Long.parseLong(req.getParameter("id")));
						pm.close();
						req.setAttribute("resource", resource2);
						req.getRequestDispatcher("/WEB-INF/View/Resource/edit.jsp").forward(req, resp);
					}
				}
			}
		}
		
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Resource resource = pm.getObjectById(Resource.class, Long.parseLong(req.getParameter("id")));
		boolean status = Boolean.parseBoolean(req.getParameter("status"));
		String url = req.getParameter("url");

		String query = "SELECT FROM " + Resource.class.getName();
		List<Resource> resources = (List<Resource>) pm.newQuery(query).execute();
		boolean duplicado = false;
		for (Resource res : resources) {
			if (res.getUrl().equals(url) && (!res.getId().equals(resource.getId()))) {
				duplicado = true;
			}
		}
		if (duplicado) {
			pm.close();
			String error = "ELEMENTO EDITADO DUPLICADO";
			req.setAttribute("error", error);
			req.getRequestDispatcher("/WEB-INF/View/Resource/error2.jsp").forward(req, resp);
		} else {
			resource.setStatus(status);
			resource.setUrl(url);
			pm.close();
			resp.sendRedirect("/resource/view?id=" + req.getParameter("id"));
		}

	}
}