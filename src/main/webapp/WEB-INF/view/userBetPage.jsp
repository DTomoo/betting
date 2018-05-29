<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty errorMessages}">
	<ul class="errorMessages">
		<c:forEach items="${errorMessages}" var="msg">
			<li>${errorMessages}</li>
		</c:forEach>
	</ul>
</c:if>


<c:if test="${not empty userData}">

	<h1>${userData.name }tippjei</h1>

	<table class="table table-hover">
		<thead>
			<th>Mérkőzés</th>
			<th>Tipp</th>
			<th></th>
		</thead>
		<tbody>
			<c:if test="${!empty userData}">
				<c:forEach var="bet" items="${userData.bets}">
					<tr>
						<td>${bet.matchName}</td>
						<td>${bet.getShortText()}</td>
						<td><button match-id="${bet.matchId}" onclick="bettingApp.loadMatch(this);">Ugrás a mérkőzéshez</button></td>
					</tr>
				</c:forEach>
			</c:if>

		</tbody>
	</table>

</c:if>
