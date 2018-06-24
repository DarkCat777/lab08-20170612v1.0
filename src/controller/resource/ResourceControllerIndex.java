package controller.resource;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import controller.PMF;
import model.*;
//envia el paramentro billing a /WEB-INF/Views/Billing/index.jsp
@SuppressWarnings("serial")
public class ResourceControllerIndex  extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "SELECT FROM "+ Resource.class.getName();
		List<Resource> resources =(List<Resource>)pm.newQuery(query).execute();
		pm.close();
		req.setAttribute("resources", resources);
		req.getRequestDispatcher("/WEB-INF/View/Resource/index.jsp").forward(req, resp);
	}
}
