<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>${championship.name}</h1>

<c:if test="${not empty errorMessages}">
	<ul class="errorMessages">
		<c:forEach items="${errorMessages}" var="msg">
			<li>${errorMessages}</li>
		</c:forEach>
	</ul>
</c:if>

<table class="table table-hover">
	<thead>
		<th>#</th>
		<th>Csapat neve</th>
	</thead>
	<tbody>
		<c:if test="${not empty championship.teams}">
			<c:forEach var="team" items="${championship.teams}">
				<tr>
					<td>${team.id}</td>
					<td>${team.name}</td>
				</tr>
			</c:forEach>
		</c:if>

		<c:if test="${admin}">
			<tr>
				<td>+</td>
				<td colspan="10">
					<form>
						<input type="text" name="teamName" data-championship-id="${championship.id}"
								onkeyup="bettingApp.addTeamOnEnter(event);"
								onblur="bettingApp.addTeam(event);" />
					</form>
				</td>
			</tr>
		</c:if>

	</tbody>
</table>

