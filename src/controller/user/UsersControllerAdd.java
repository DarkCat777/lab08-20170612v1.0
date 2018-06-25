package controller.user;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Persistent;
import javax.servlet.ServletException;
import controller.PMF;
import model.Access;
import model.Resource;
import model.Role;
import model.Users;

@SuppressWarnings("serial")
public class UsersControllerAdd extends HttpServlet {
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
						String query4 = "SELECT FROM " + Role.class.getName();
						List<Role> roles = (List<Role>) pm.newQuery(query4).execute();
						pm.close();
						req.setAttribute("roles", roles);
						req.getRequestDispatcher("/WEB-INF/View/Users/add.jsp").forward(req, resp);
					}
				}
			}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");// dd/MM/yyyy
		String createReq = req.getParameter("create");
		String birthReq = req.getParameter("birth");
		String email = req.getParameter("email");
		Date birth = null;
		boolean status = Boolean.parseBoolean(req.getParameter("status"));
		Date create = null;
		boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
		Long idRole = Long.parseLong(req.getParameter("idrole"));
		try {
			create = formatoDelTexto.parse(createReq);
			birth = formatoDelTexto.parse(birthReq);
		} catch (ParseException e) {
			resp.getWriter().println(e.getMessage());
		}
		try {
			Users user = new Users(email, birth, status, create, gender, idRole);
			String query = "SELECT FROM " + Users.class.getName();
			List<Users> users = (List<Users>) pm.newQuery(query).execute();
			boolean duplicado = false;
			for (Users us : users) {
				if (us.getEmail().equals(user.getEmail())) {
					duplicado = true;
				}
			}
			if (duplicado) {
				pm.close();
				String error = "ELEMENTO DUPLICADO";
				req.setAttribute("error", error);
				req.getRequestDispatcher("/WEB-INF/View/Users/error2.jsp").forward(req, resp);
			} else {
				pm.makePersistent(user);
				pm.close();
				resp.sendRedirect("/user");// ControllerIndex.java
			}

		} catch (Exception e) {
			resp.getWriter().println(e.getMessage());
		}
	}
}