<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Mérkőzések</h1>

<c:if test="${not empty dataset.errorMessages}">
	<ul class="errorMessages">
		<c:forEach items="${dataset.errorMessages}" var="msg">
			<li>${dataset.errorMessages}</li>
		</c:forEach>
	</ul>
</c:if>

<table class="table table-hover">
	<thead>
		<th>Csapat 1</th>
		<th>Csapat 2</th>
		<th>Időpont</th>
		<th>Státusz</th>
		<th>Eredmény</th>
		<th>Tipp</th>
	</thead>
	<tbody>
		<c:if test="${!empty dataset.matches}">
			<c:forEach var="match" items="${dataset.matches}">
				<tr match-id="${match.matchId}" onclick="bettingApp.loadMatch(this);" style="cursor:pointer">
					<td>${match.team1.name}</td>
					<td>${match.team2.name}</td>
					<td>${match.dateTime}</td>
					<td>${match.status.text}</td>
					<td>${match.gameStatistics}</td>
					<td><c:choose>
							<c:when test="${empty match.userBet}">
								<button ">Tipp!</button>
							</c:when>
							<c:otherwise>${match.userBet.getShortText()}</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</c:if>

	</tbody>
</table>
