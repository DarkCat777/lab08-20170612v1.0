package controller.resource;
import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import com.google.api.server.spi.auth.common.User;

import controller.access.AccessControllerDelete;

import controller.PMF;
import model.Access;
import model.Resource;
import model.Users;

@SuppressWarnings("serial")
public class ResourceControllerDelete extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Long idResource=Long.parseLong(req.getParameter("id"));
		Resource resource = pm.getObjectById(Resource.class, idResource);
		if (resource!=null){
			//Selecionando y Borrando Acces implicados con los recursos
			String query="SELECT FROM "+Access.class.getName()+" WHERE idUrl=="+idResource;
			List<Access> access=(List<Access>)pm.newQuery(query).execute();
			for (Access acce : access) {
				AccessControllerDelete.delete(acce.getId());
			}
			pm.deletePersistent(resource);
		}
		pm.close();
		resp.sendRedirect("/resource");
	}
}