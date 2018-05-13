<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<h1>User page</h1>

<table>
	<thead>
		<th>ID</th>
		<th>Name</th>
	</thead>
	<tbody>
		<c:if test="${!empty users}">
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.id}</td>
					<td>${user}</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<td>+</td>
			<td>
				<form action="/ds/user/add">
					<input type="text" name="userName"></input>
				</form>
			</td>
		</tr>
	</tbody>
</table>


