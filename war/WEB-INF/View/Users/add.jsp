<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Role"%>
<%
	List<Role> roles = (List<Role>) request.getAttribute("roles");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="es">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ACL - Home</title>
<link rel="stylesheet" type="text/css" href="/CSS/reset.css" />
<link rel="stylesheet" type="text/css" href="/CSS/main.css" />
</head>

<body>
	<div id="header">
		<div class="container">
			<h1>
				<a href="/user">ACL Laboratorio 8</a>
			</h1>
			<div id="main_menu">
				<ul>
					<li class="first_list"><a href="/user"
						class="main_menu_first main_current">home</a></li>
					<li class="first_list with_dropdown"><a href="#"
						class="main_menu_first">Lista de Control de Acceso (ACL)</a>
						<ul class="first">
							<li class="second_list_border"><a href="/user"
								class="main_menu_second">usuario</a></li>
							<li class="second_list_border"><a href="/billing"
								class="main_menu_second">facturas</a></li>
							<li class="second_list_border"><a href="/role"
								class="main_menu_second">roles</a></li>
							<li class="second_list_border"><a href="/access"
								class="main_menu_second">access</a></li>
							<li class="second_list_border"><a href="/resource"
								class="main_menu_second">resources</a></li>
						</ul></li>
					<li class="first_list with_dropdown"><a href="#"
						class="main_menu_first">login</a>
						<ul class="second">
							<li class="second_list_border"><a href="/user/logout"
								class="main_menu_second">Log Out</a></li>
							<li class="second_list_border"><a href="/user/login"
								class="main_menu_second">Log In</a></li>
						</ul></li>
					<li class="first_list"><a href="/user"
						class="main_menu_first main_current">CUI: 20170612</a></li>
				</ul>
			</div>
			<!-- END #main_menu -->
		</div>
		<!-- END .container -->
	</div>
	<!-- END #header -->
	<div id="contenido">
		<div class="titulo">
			<h1>Añadir Usuario</h1>
		</div>
		<div class="formulario">
			<form action="/user/add" method="post">
				<label><b>Email: </b></label>
				<br>
				<input type="text" name="email" placeholder="Email" required>
				<br>
				<br>
				<label><b>Fecha de Creación: </b></label>
				<br>
				<input type="text" name="create"
					placeholder="Fecha de cumpleaños DD/MM/YY" required>
				<br>
				<br>
				<label><b>Genero: </b></label>
				<br>
				<select name="gender" size="1">
					<option value="true">Masculino</option>
					<option value="false">Femenino</option>
				</select>
				<label><b>Estado: </b></label>
				<br>
				<select name="status" size="1">
					<option value="true">True</option>
					<option value="false">False</option>
				</select>
				<label><b>Fecha de Cumpleaños: </b></label>
				<br>
				<input type="text" name="birth"
					placeholder="Fecha de cumpleaños DD/MM/YY" required>
				<br>
				<br>
				<label><b>Roles: </b></label>
				<br>
				<%
					if (roles.isEmpty()) {
				%>
				<a href="/role/add">Crear un Role</a>
				<%
					} else {
				%>
				<select name="idrole" size="1">
					<%
						for (Role role : roles) {
					%>
					<option value="<%=role.getId()%>"><%=role.getName()%></option>
					<%
						}
					%>
				</select>
				<%
					}
				%>

				<input class="boton" type="submit" value="Añadir Role">
			</form>
		</div>
		<div class=link>
			<a href="/user" title="Añadir Role"><b>Lista de Usuarios</b></a>
		</div>
	</div>
</body>

</html>