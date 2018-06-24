<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Billing"%>
<%
	List<Billing> billings = (List<Billing>) request.getAttribute("billings");
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
			<h1>Añadir Factura</h1>
		</div>
		<div class="formulario">
			<form action="/billing/add" method="post">
				<label><b>Nombre de la Empresa: </b></label>
				<br>
				<input type="text" name="customer"
					placeholder="Ingrese el nombre de la empresa" required>
				<br>
				<br>
				<label><b>Dirección:</b></label>
				<br>
				<input type="text" name="address"
					placeholder="Ingrese la direccion de la empresa" required>
				<br>
				<br>
				<label><b>Producto:</b></label>
				<br>
				<input type="text" name="descriptionproduct"
					placeholder="Ingrese la nombre del producto" required>
				<br>
				<br>
				<label><b>Precio Unitario:</b></label>
				<br>
				<input type="text" name="unitpriceproduct"
					placeholder="Ingrese precio unitario del producto" required>
				<br>
				<br>
				<label><b>Cantidad:</b></label>
				<br>
				<input type="text" name="mount"
					placeholder="Ingrese la direccion de la empresa" required>
				<br>
				<br>
				<input class="boton" type="submit" value="Añadir">
			</form>
		</div>
		<div class=link>
			<a href="/billing" title="Añadir Factura"><b>Lista de
					Facturas</b></a>
		</div>
	</div>
</body>

</html>