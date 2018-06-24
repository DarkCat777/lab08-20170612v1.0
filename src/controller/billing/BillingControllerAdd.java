package controller.billing;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import controller.PMF;
import model.Access;
import model.Billing;
import model.Resource;
import model.Users;

@SuppressWarnings("serial")
public class BillingControllerAdd extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if (user == null) {
			req.getRequestDispatcher("/user/login").forward(req, resp);
		} else {
			String query = "SELECT FROM " + Users.class.getName() + " WHERE email=='" + user.getEmail()
					+ "' && status==true";
			List<Users> users = (List<Users>) pm.newQuery(query).execute();
			if (users.isEmpty()) {
				// ERROR NO EXISTE UN USUARIO O NO ESTA ACTIVO
				String codigoError = "ERROR NO ES UN USUARIO REGRISTRADO O NO ESTA ACTIVO";
				req.setAttribute("error", codigoError);
				req.getRequestDispatcher("/WEB-INF/View/Users/error.jsp").forward(req, resp);
			} else {
				String query2 = "SELECT FROM " + Resource.class.getName() + " WHERE url=='" + req.getServletPath()
						+ "' && status==true";
				List<Resource> resource = (List<Resource>) pm.newQuery(query2).execute();
				if (resource.isEmpty()) {
					// ERROR NO EXISTE UN RECURSO O NO ESTA ACTIVO
					String codigoError = "ERROR NO EXISTE UN RECURSO O NO ESTA ACTIVO";
					req.setAttribute("error", codigoError);
					req.getRequestDispatcher("/WEB-INF/View/Users/error.jsp").forward(req, resp);
				} else {
					String query3 = "SELECT FROM " + Access.class.getName() + " WHERE idRole=="
							+ users.get(0).getIdRole() + " && idUrl==" + resource.get(0).getId() + " && status==true";
					List<Access> access = (List<Access>) pm.newQuery(query3).execute();
					if (access.isEmpty()) {
						// ERROR NO EXISTE UN ACCESO O NO ESTA ACTIVO
						String codigoError = "ERROR NO EXISTE UN ACCESO O NO ESTA ACTIVO";
						req.setAttribute("error", codigoError);
						req.getRequestDispatcher("/WEB-INF/View/Users/error.jsp").forward(req, resp);
					} else {
						// Servlet Original
						req.getRequestDispatcher("/WEB-INF/View/Billing/add.jsp").forward(req, resp);
					}
				}
			}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User userInLine = UserServiceFactory.getUserService().getCurrentUser();
		String query = "SELECT FROM " + Users.class.getName() + " WHERE email=='" + userInLine.getEmail()
				+ "' && status==true";
		List<Users> user = (List<Users>) pm.newQuery(query).execute();
		Long idUser = null;
		if (!user.isEmpty()) {
			idUser = user.get(0).getId();
		}
		String customer = req.getParameter("customer");
		String address = req.getParameter("address");
		boolean status = true;
		String descriptionProduct = req.getParameter("descriptionproduct");
		double unitPriceProduct = Double.parseDouble(req.getParameter("unitpriceproduct"));
		double mountProduct = Double.parseDouble(req.getParameter("mount"));
		// Falta pensar como hacerlo
		try {
			Billing billing = new Billing(customer, address, status, descriptionProduct, unitPriceProduct, mountProduct,
					idUser);
			pm.makePersistent(billing);
			pm.close();
			resp.sendRedirect("/billing");// ControllerIndex.java
		} catch (Exception e) {
			resp.getWriter().println(e.getMessage());
		}
	}
}