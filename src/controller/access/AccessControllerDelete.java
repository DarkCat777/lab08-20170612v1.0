package controller.access;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import controller.PMF;
import model.Access;
import model.Resource;
import model.Users;

@SuppressWarnings("serial")
public class AccessControllerDelete extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Long id = Long.parseLong(req.getParameter("id"));
		delete(id);
		resp.sendRedirect("/access");
	}

	public static void delete(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Access access = pm.getObjectById(Access.class, id);
		if (access != null) {
			pm.deletePersistent(access);
		}
		pm.close();
	}
}