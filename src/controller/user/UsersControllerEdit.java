package controller.user;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import model.Role;
import model.Users;

@SuppressWarnings("serial")
public class UsersControllerEdit extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user2 = pm.getObjectById(Users.class, Long.parseLong(req.getParameter("id")));
		String query4 = "SELECT FROM " + Role.class.getName();
		List<Role> roles = (List<Role>) pm.newQuery(query4).execute();
		pm.close();
		req.setAttribute("roles", roles);
		req.setAttribute("user", user2);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/View/Users/edit.jsp");
		rd.forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user = pm.getObjectById(Users.class, Long.parseLong(req.getParameter("id")));
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		String createReq = req.getParameter("create");
		String birthReq = req.getParameter("birth");
		String email = req.getParameter("email");
		Date birth = null;
		boolean status = Boolean.parseBoolean(req.getParameter("status"));
		Date create = null;
		boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
		Long idRole = Long.parseLong(req.getParameter("idrole"));
		try {
			create = formatoDelTexto.parse(createReq);
			birth = formatoDelTexto.parse(birthReq);
		} catch (ParseException e) {
			resp.getWriter().println(e.getMessage());
		}

		String query = "SELECT FROM " + Users.class.getName();
		List<Users> users = (List<Users>) pm.newQuery(query).execute();
		boolean duplicado = false;
		for (Users us : users) {
			if (us.getEmail().equals(email) && (!us.getId().equals(user.getId()))) {
				duplicado = true;
			}
		}
		if (duplicado) {
			pm.close();
			String error = "ELEMENTO EDITADO DUPLICADO";
			req.setAttribute("error", error);
			req.getRequestDispatcher("/WEB-INF/View/Users/error2.jsp").forward(req, resp);
		} else {
			user.setBirth(birth);
			user.setCreate(create);
			user.setEmail(email);
			user.setGender(gender);
			user.setStatus(status);
			user.setIdRole(idRole);
			pm.close();
			resp.sendRedirect("/user/view?id=" + req.getParameter("id"));
		}
	}

	public static void changeIdRole(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user = pm.getObjectById(Users.class, id);
		user.setIdRole(null);
		pm.close();
	}
}