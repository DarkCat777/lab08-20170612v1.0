package controller.role;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.*;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import controller.PMF;
import model.Role;
import model.Users;

@SuppressWarnings("serial")
public class RoleControllerAdd extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("/WEB-INF/View/Role/add.jsp").forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String name = req.getParameter("name");
		boolean status = Boolean.parseBoolean(req.getParameter("status"));
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
		String strFecha = req.getParameter("create");
		Date create = null;
		try {
			create = formatoDelTexto.parse(strFecha);
		} catch (ParseException e) {
			resp.getWriter().println(e.getMessage());
		}

		String query = "SELECT FROM " + Role.class.getName();
		List<Role> roles = (List<Role>) pm.newQuery(query).execute();
		boolean duplicado = false;
		for (Role rol : roles) {
			if (rol.getName().equalsIgnoreCase(name)) {
				duplicado = true;
			}
		}
		if (duplicado) {
			pm.close();
			String error = "ELEMENTO DUPLICADO";
			req.setAttribute("error", error);
			req.getRequestDispatcher("/WEB-INF/View/Role/error2.jsp").forward(req, resp);
		} else {
			Role role = new Role(name, create, status);
			pm.makePersistent(role);
			pm.close();
			resp.sendRedirect("/role");
		}
	}
}