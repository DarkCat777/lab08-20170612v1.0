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
public class ResourceControllerAdd extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("/WEB-INF/View/Resource/add.jsp").forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String url = req.getParameter("url").toLowerCase();
		boolean status = Boolean.parseBoolean(req.getParameter("status"));

		String query = "SELECT FROM " + Resource.class.getName();
		List<Resource> resources = (List<Resource>) pm.newQuery(query).execute();
		boolean duplicado = false;
		for (Resource res : resources) {
			if (res.getUrl().equals(url)) {
				duplicado = true;
			}
		}
		if (duplicado) {
			pm.close();
			String error = "ELEMENTO DUPLICADO";
			req.setAttribute("error", error);
			req.getRequestDispatcher("/WEB-INF/View/Resource/error2.jsp").forward(req, resp);
		} else {
			Resource resource = new Resource(url, status);
			pm.makePersistent(resource);
			pm.close();
			resp.sendRedirect("/resource");// ControllerIndex.java
		}
	}
}