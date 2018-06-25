package controller.role;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import controller.PMF;
import controller.access.AccessControllerDelete;
import controller.user.UsersControllerEdit;
import model.Access;
import model.Resource;
import model.Role;
import model.Users;

@SuppressWarnings("serial")
public class RoleControllerDelete extends HttpServlet {
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

						Long idRole = Long.parseLong(req.getParameter("id"));
						Role role = pm.getObjectById(Role.class, idRole);
						if (role != null) {
							// Seleccionando y borrando Accesos
							String query4 = "SELECT FROM " + Access.class.getName() + " WHERE idRole==" + idRole;
							List<Access> access2 = (List<Access>) pm.newQuery(query4).execute();
							if (!access.isEmpty()) {
								for (Access acce : access2) {
									AccessControllerDelete.delete(acce.getId());
								}
							}
							// Selecionando y cambiando idUser=null en los
							// Usuarios Afectados
							String query5 = "SELECT FROM " + Users.class.getName() + " WHERE idRole==" + idRole;
							List<Users> users2 = (List<Users>) pm.newQuery(query5).execute();
							if (!users.isEmpty()) {
								for (Users us : users2) {
									UsersControllerEdit.changeIdRole(us.getId());
								}
							}
							pm.deletePersistent(role);
						}
						pm.close();
						resp.sendRedirect("/role");
					}
				}
			}
		}

	}
}