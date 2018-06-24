<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Billing"%>
<%@page import="java.util.Date"%>
<%
	Billing billing = (Billing) request.getAttribute("billing");
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
			<h2>Editar Factura</h2>
		</div>
		<div class="formulario">
			<form action="/billing/edit" method="post">
				<label><b>Editar el nombre de la empresa: </b></label>
				<br>
				<input type="text" name="customer"
					placeholder="Ingrese el nombre de la emrpresa"
					value="<%=billing.getCustomer()%>" required>
				<br>
				<br>
				<label><b>Editar la dirección: </b></label>
				<br>
				<input type="text" name="address"
					placeholder="Ingrese la direccion de la empresa"
					value="<%=billing.getAddress()%>" required>
				<br>
				<br>
				<label><b>Editar el producto: </b></label>
				<br>
				<input type="text" name="descriptionproduct"
					placeholder="Ingrese el nombre del producto"
					value="<%=billing.getDescriptionProduct()%>" required>
				<br>
				<br>
				<label><b>Editar la unidad de producto: </b></label>
				<br>
				<input type="text" name="unitpriceproduct"
					placeholder="Ingrese la unidad de producto"
					value="<%=billing.getUnitPriceProduct()%>" required>
				<br>
				<br>
				<label><b>Editar la cantidad de productos: </b></label>
				<br>
				<input type="text" name="mountProduct"
					placeholder="Ingrese la cantidad de productos"
					value="<%=billing.getMountProduct()%>" required>
				<br>
				<br>
				<input type="hidden" name="id" value="<%=billing.getId()%>">
				<input class="boton" type="submit" value="Editar">
			</form>
		</div>
		<div class="link">
			<a id="add" href="/billing" title="Lista de Facturas">Lista de
				Facturas</a>
		</div>
	</div>
</body>

</html>