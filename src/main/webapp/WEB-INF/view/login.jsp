<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml" >

<head>
<title>Házi fogadás - Belépés</title>

<!-- Required meta tags -->
<meta charset="utf-8">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, shrink-to-fit=no">

		<!-- Bootstrap CSS -->
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet"
			href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

			<!-- Optional theme -->
			<link rel="stylesheet"
				href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
</head>
<body>
	<c:if test="${not empty errorMessages}">
		<ul class="errorMessages">
			<c:forEach items="${errorMessages}" var="msg">
				<li>${errorMessages}</li>
			</c:forEach>
		</ul>
	</c:if>

	<c:if test="${not empty infoMessages}">
		<ul class="infoMessages">
			<c:forEach items="${infoMessages}" var="msg">
				<li>${infoMessages}</li>
			</c:forEach>
		</ul>
	</c:if>

	<div class="container">
		<div class="jumbotron">
			<form method="post">
				<table>
					<c:if test="${logout}">
						<tr>
							<td colspan="2"><p style="color:red">Sikeresen kijelentkeztél.</p></td>
						</tr>
					</c:if>
					<c:if test="${error}">
						<tr>
							<td colspan="2"><p style="color:red">Helytelen felhasználónév vagy jelszó!</p></td>
						</tr>
					</c:if>				
				
					<tr>
						<td>Felhasználónév:</td>
						<td><input type="text" name="username" /></td>
					</tr>
					<tr>
						<td>Jelszó:</td>
						<td><input type="password" name="password" /></td>
					</tr>
				</table>

				<div>
					<input type="submit" value="Belépés" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>