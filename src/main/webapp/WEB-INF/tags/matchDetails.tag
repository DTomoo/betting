<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>

<%@ attribute name="matches" required="true" type="java.util.List" %>
<%@ attribute name="teams" required="true" type="java.util.List" %>
<%@ attribute name="championshipId" required="true" type="java.lang.Long" %>
<%@ attribute name="roundId" required="true" type="java.lang.Long" %>
<%@ attribute name="groupId" required="true" type="java.lang.Long" %>

<div class="table-responsive">
<table class="table table-hover ">
	<thead>
		<c:if test="${admin}">
			<th>#</th>
		</c:if>
		<th>Csapat 1</th>
		<th>Csapat 2</th>
		<th>Időpont</th>
		<th>Státusz</th>
		<th>Eredmény</th>
		<th>Tipp</th>
	</thead>
	<tbody>
		<c:if test="${!empty matches}">
			<c:forEach var="match" items="${matches}">
				<tr data-match-id="${match.id}" onclick="bettingApp.loadMatch(event);" style="cursor:pointer">
					<c:if test="${admin}">
					<td>${match.id}</td>
					</c:if>
					<td>${match.team1.name}</td>
					<td>${match.team2.name}</td>
					<td>${match.dateTime}</td>
					<td>${match.status.text}</td>
					<td>${match.result.betPieces['FINAL_RESULT']}</td>
					<td><c:choose>
							<c:when test="${empty match.userBet and match.status eq 'NEW'}">
								<button data-match-id="${match.id}">Tipp!</button>
							</c:when>
							<c:otherwise>${match.userBet.betPieces['FINAL_RESULT']}</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${admin}">
			<c:set var="dropdownGroupName" value="teamIdGroup${groupId}" />
			<tr>
				<td>+ <myTag:dropdownGroupTemplate groupName="${dropdownGroupName}" options="${teams}" /></td>
				<td>
					<select name="teamId1" dropdown-group="${dropdownGroupName}"></select>
				</td>
				<td>
					<select name="teamId2" dropdown-group="${dropdownGroupName}"></select>
				</td>
				<td colspan="10">
					<button data-championship-id="${championshipId}" data-round-id="${roundId}" data-group-id="${groupId}" 
							onclick="bettingApp.addMatch(event)">Létrehoz</button>
				</td>
			</tr>
		</c:if>
	</tbody>
</table>
</div>
<c:if test="${admin}">
<script>
bettingApp.guiControls.initDropdownGroup("${dropdownGroupName}")
</script>
</c:if>


