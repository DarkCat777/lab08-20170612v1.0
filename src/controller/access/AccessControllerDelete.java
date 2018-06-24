package controller.access;
import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import controller.PMF;
import model.Access;

@SuppressWarnings("serial")
public class AccessControllerDelete extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Long id=Long.parseLong(req.getParameter("id"));
		delete(id);
		resp.sendRedirect("/access");
	}
	public static void delete(Long id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Access access = pm.getObjectById(Access.class,id);
		if (access!=null){
			pm.deletePersistent(access);
		}
		pm.close();
	}
}