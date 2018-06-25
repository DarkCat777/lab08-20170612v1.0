package controller.resource;

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
import model.Users;;

public class ResourceControllerView extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Resource resource2 = pm.getObjectById(Resource.class, Long.parseLong(req.getParameter("id")));
		pm.close();
		req.setAttribute("resource", resource2);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/View/Resource/view.jsp");
		rd.forward(req, resp);
		// req.getRequestDispatcher("/view.jsp").forward(req,
		// resp);
	}
}