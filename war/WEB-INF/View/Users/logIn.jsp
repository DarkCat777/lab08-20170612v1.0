<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.users.*"%>
<%
	User user = (User) request.getAttribute("user");
	UserService us = UserServiceFactory.getUserService();
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
								<li><a href="index.html">Inicio</a></li>
								<li><a href="/user">Usuarios</a></li>
								<li><a href="/role">Roles</a></li>
								<li><a href="/access">Accesos</a></li>
								<li><a href="/resource">Recursos</a></li>
								<li><a href="/billing">Facturas</a></li>
								<% if(us.isUserLoggedIn()){%>
									<li class="active"><a href="/user/login"><%=user.getNickname()%></a></li>
								<% }else{%>
									<li class="active"><a href="/user/login">Login</a></li>
								<%} %>
								<li><a href="/user/logout">LogOut</a></li>
							</ul>
						</nav>
					</div>
				</div>
				<div class="inner cover">
					<div class="contenido" >
						<h1 class="cover-heading">
							<b>Usuario Logeado.</b>
						</h1>
						<div id=infouser>
							<br>
							<h3>Nombre del Usuario:</h3>
							<p><%=user.getNickname()%></p>
							<h3>Email del Usuario:</h3>
							<p><%=user.getEmail()%></p>
							<h3>Dominio del email:</h3>
							<p><%=user.getAuthDomain()%></p>
							<h3>Id del Usuario:</h3>
							<p><%=user.getUserId()%></p>
							<br>
						</div>
					</div>
				</div>


				<div class="mastfoot">
					<div class="inner">
						<p>
							Facebook: <a
								href="https://www.facebook.com/erickdavid.carpiohachiri">Erick
								David Carpio Hachiri</a>, CUI <a href="ecarpioha@unsa.edu.pe">20170612</a>.
						</p>
					</div>
				</div>

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
