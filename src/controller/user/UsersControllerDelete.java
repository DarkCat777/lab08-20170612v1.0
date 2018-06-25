package controller.user;

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
public class UsersControllerDelete extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user2 = pm.getObjectById(Users.class, new Long(req.getParameter("id")).longValue());
		if (user2 != null) {
			if ((!user2.getEmail().equalsIgnoreCase("nekitoedmh@gmail.com"))) {
				pm.deletePersistent(user2);
			}
		}
		pm.close();
		resp.sendRedirect("/user");
	}
}