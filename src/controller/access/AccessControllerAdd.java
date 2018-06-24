package controller.access;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.*;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import controller.PMF;
import model.Access;
import model.Resource;
import model.Role;

@SuppressWarnings("serial")
public class AccessControllerAdd extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query2 = "SELECT FROM " + Role.class.getName();
		String query3 = "SELECT FROM " + Resource.class.getName();
		List<Role> roles = (List<Role>) pm.newQuery(query2).execute();
		List<Resource> resources = (List<Resource>) pm.newQuery(query3).execute();
		pm.close();
		req.setAttribute("roles", roles);
		req.setAttribute("resources", resources);
		req.getRequestDispatcher("/WEB-INF/View/Access/add.jsp").forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		boolean status = Boolean.parseBoolean(req.getParameter("status"));
		Long idRole = Long.parseLong(req.getParameter("idrole"));
		Long idResource = Long.parseLong(req.getParameter("idresource"));
		
		
		String query = "SELECT FROM " + Access.class.getName();
		List<Access> access2 = (List<Access>) pm.newQuery(query).execute();
		boolean duplicado = false;
		for (Access acc : access2) {
			if (acc.getIdRole().equals(idRole)&&acc.getIdUrl().equals(idResource)) {
				duplicado = true;
			}
		}
		if (duplicado) {
			pm.close();
			String error = "ELEMENTO DUPLICADO";
			req.setAttribute("error", error);
			req.getRequestDispatcher("/WEB-INF/View/Access/error2.jsp").forward(req, resp);
		} else {
			Access access = new Access(idRole, idResource, status);
			pm.makePersistent(access);
			pm.close();
			resp.sendRedirect("/access");// ControllerIndex.java
		}
		
		


	}
}