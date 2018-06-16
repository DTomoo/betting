<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>

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
				<tr data-match-id="${match.id}" onclick="bettingApp.loadMatch(this);" style="cursor:pointer">
					<td>${match.id}</td>
					<td>${match.team1.name}</td>
					<td>${match.team2.name}</td>
					<td>${match.dateTime}</td>
					<td>${match.status.text}</td>
					<td>${match.result.betPieces['FINAL_RESULT']}</td>
					<td><c:choose>
							<c:when test="${empty match.userBet}">
								<button>Tipp!</button>
							</c:when>
							<c:otherwise>${match.userBet.betPieces['FINAL_RESULT']}</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${dataset.allowNewDataToAdd}">
			<tr>
				<td>+ <myTag:dropdownGroupTemplate groupName="teamIdGroup" options="${dataset.teams}" /></td>
				<td>
					<select name="teamId1" dropdown-group="teamIdGroup"></select>
				</td>
				<td>
					<select name="teamId2" dropdown-group="teamIdGroup"></select>
				</td>
				<td colspan="10">
					<button onclick="bettingApp.addMatch(this)">Létrehoz</button>
				</td>
			</tr>
		</c:if>
	</tbody>
</table>

<h1>Csapatok</h1>

<table class="table table-hover">
	<thead>
		<th>#</th>
		<th>Csapat neve</th>
	</thead>
	<tbody>
		<c:if test="${not empty dataset.teams}">
			<c:forEach var="team" items="${dataset.teams}">
				<tr>
					<td>${team.id}</td>
					<td>${team.name}</td>
				</tr>
			</c:forEach>
		</c:if>
		
		<c:if test="${dataset.allowNewDataToAdd}">
			<tr>
				<td>+</td>
				<td colspan="10">
					<input type="text" name="teamName" onkeyup="bettingApp.addTeamOnEnter(event);" onblur="bettingApp.addTeam(event);"></input>
				</td>
			</tr>
		</c:if>
		
	</tbody>
</table>

<script>
bettingApp.guiControls.initDropdownGroup("teamIdGroup")
</script>
