package controller.user;
import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import controller.PMF;
import model.Users;

@SuppressWarnings("serial")
public class UsersControllerDelete extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user = pm.getObjectById(Users.class, new Long(req.getParameter("id")).longValue());
		if (user!=null){
			pm.deletePersistent(user);
		}
		pm.close();
		resp.sendRedirect("/user");
	}
}