<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Játékosok</h1>

<c:if test="${not empty errorMessages}">
	<ul class="errorMessages">
		<c:forEach items="${errorMessages}" var="msg">
			<li>${errorMessages}</li>
		</c:forEach>
	</ul>
</c:if>

<table class="table table-hover">
	<thead>
		<th>ID</th>
		<th>Név</th>
		<th>Pontok</th>
		<c:if test="${allowNewDataToAdd}">
			<th></th>
		</c:if>
	</thead>
	<tbody>
		<c:if test="${!empty users}">
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.id}</td>
					<td>${user.name}</td>
					<td>${user.scores}</td>
					<c:if test="${allowNewDataToAdd}">
						<td><button data-user-id="${user.id}" onclick="bettingApp.loadUser(this);">Tippjei</button></td>
					</c:if>
				</tr>
			</c:forEach>
		</c:if>


		<c:if test="${allowNewDataToAdd}">
			<tr>
				<td>+</td>
				<td colspan="10">
					<input type="text" name="userName" onkeyup="bettingApp.addUserOnEnter(event);" onblur="bettingApp.addUser(this);"></input>
				</td>
			</tr>
		</c:if>
	</tbody>
</table>
