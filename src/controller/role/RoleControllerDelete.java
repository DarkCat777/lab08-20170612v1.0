package controller.role;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import controller.PMF;
import controller.access.AccessControllerDelete;
import controller.user.UsersControllerEdit;
import model.Access;
import model.Role;
import model.Users;

@SuppressWarnings("serial")
public class RoleControllerDelete extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Long idRole = Long.parseLong(req.getParameter("id"));
		Role role = pm.getObjectById(Role.class, idRole);
		if (role != null) {
			//Seleccionando y borrando Accesos
			String query = "SELECT FROM " + Access.class.getName() + " WHERE idRole==" + idRole;
			List<Access> access = (List<Access>) pm.newQuery(query).execute();
			if (!access.isEmpty()) {
				for (Access acce : access) {
					AccessControllerDelete.delete(acce.getId());
				}
			}
			//Selecionando y cambiando idUser=null en los Usuarios Afectados
			String query2="SELECT FROM "+Users.class.getName()+" WHERE idRole=="+idRole;
			List<Users> users = (List<Users>) pm.newQuery(query2).execute();
			if(!users.isEmpty()){
				for (Users us : users) {
					UsersControllerEdit.changeIdRole(us.getId());
				}
			}
			pm.deletePersistent(role);
		}
		pm.close();
		resp.sendRedirect("/role");
	}
}