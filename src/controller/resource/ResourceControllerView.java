package controller.resource;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import controller.PMF;
import model.Resource;;

public class ResourceControllerView extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Resource resource = pm.getObjectById(Resource.class, Long.parseLong(req.getParameter("id")));
		pm.close();
		req.setAttribute("resource", resource);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/View/Resource/view.jsp");
		rd.forward(req, resp);
		//req.getRequestDispatcher("/view.jsp").forward(req, resp);
	}
}