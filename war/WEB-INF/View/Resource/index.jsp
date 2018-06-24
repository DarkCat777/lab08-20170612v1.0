<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Resource"%>
<%@ page import="com.google.appengine.api.users.*"%>
<%
	List<Resource> resources = (List<Resource>) request.getAttribute("resources");
	UserService use = UserServiceFactory.getUserService();
	User user = use.getCurrentUser();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->


<title>ACL - Home</title>

<!-- Bootstrap core CSS -->
<link href="/CSS/bootstrap.min.css" rel="stylesheet">

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<link href="/CSS/ie10-viewport-bug-workaround.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/CSS/cover.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="/JS/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<style>
#data {
	align-items: center;
	padding: 30px;
}

#contenido {
	align-text: center;
}

.contenido {
	background-color: white;
	color: black;
}

.lista {
	background-color: black;
	color: white;
	width: 100%;
}
</style>
</head>

<body>

	<div class="site-wrapper">

		<div class="site-wrapper-inner">

			<div class="cover-container">

				<div class="masthead clearfix">
					<div class="inner">
						<h3 class="masthead-brand">
							<b>Access Control List (ACL)</b>
						</h3>
						<nav>
							<ul class="nav masthead-nav">
								<li><a href="/index.html">Inicio</a></li>
								<li><a href="/user">Usuarios</a></li>
								<li><a href="/role">Roles</a></li>
								<li><a href="/access">Accesos</a></li>
								<li class="active"><a href="/resource">Recursos</a></li>
								<li><a href="/billing">Facturas</a></li>
								<%
									if (use.isUserLoggedIn()) {
								%>
								<li class="active"><a href="/user/login"><%=user.getNickname()%></a></li>
								<%
									} else {
								%>
								<li><a href="/user/login">Login</a></li>
								<%
									}
								%>
								<li><a href="/user/logout">LogOut</a></li>
							</ul>
						</nav>
					</div>
				</div>
				<div class="inner cover">
				<br><br><br><br>
					<div id="contenido">
						<div class="titulo">
							<h1>Recursos Disponibles</h1>
						</div>
						<div class="link">
							<a href="/resource/add">A�adir Recursos</a>
						</div>
						<div id="data">
							<%
								if (resources.isEmpty()) {
							%>
							<h2>No hay Recursos. A�ada Recursos</h2>
							<%
								} else {
							%>
							<table class="lista">
								<tr class="titulo">
									<td><b>URL</b></td>
									<td><b>Estado</b></td>
									<td><b>ID del Recurso</b></td>
									<td><b>Operaciones</b></td>
								</tr>
								<%
									for (Resource resou : resources) {
								%>
								<tr class="contenido">
									<td><%=resou.getUrl()%></td>
									<%if(resou.isStatus()){%>
									<td>Activo</td>
									<% }else{%>
									<td>Inactivo</td>
									<%} %>
									<td><%=resou.getId()%></td>
									<td>
										<a href="/resource/view?id=<%=resou.getId()%>">Ver</a>
										<a href="/resource/edit?id=<%=resou.getId()%>">Editar</a>
										<a href="/resource/delete?id=<%=resou.getId()%>">Borrar</a>
									</td>
								</tr>
								<%
									}
								%>
							</table>
							<%
								}
							%>
						</div>

					</div>
				</div>

				<!-- Bootstrap core JavaScript
    ================================================== -->
				<!-- Placed at the end of the document so the pages load faster -->
				<script
					src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
				<script>
					window.jQuery
							|| document
									.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')
				</script>
				<script src="/JS/bootstrap.min.js"></script>
				<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
				<script src="/JS/ie10-viewport-bug-workaround.js"></script>
</body>
</html>



