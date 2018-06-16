<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="headingText" required="true"%>
<%@ attribute name="teams" required="true" type="java.util.List"%>
<%@ attribute name="teamsOfChampionship" required="true" type="java.util.List"%>
<%@ attribute name="championshipId" required="true" type="java.lang.Long" %>
<%@ attribute name="roundId" required="true" type="java.lang.Long" %>
<%@ attribute name="groupId" required="true" type="java.lang.Long" %>

<div class="container">
	<b>Résztvevő csapatok 2</b>
	<div class="row">
		<c:forEach var="team" items="${teams}">
			<div class="col-sm-2">
				<p>${team.name}</p>
			</div>
		</c:forEach>
		
		<c:if test="${admin and teams.size() != teamsOfChampionship.size()}">
			<div class="col-sm-2">Csapat hozzáadása:</div>
			<div class="col-sm-2">
				<form>
					<select name="teamId">
						<c:forEach var="team" items="${teamsOfChampionship}">
							<c:if test="${not teams.contains(team)}">
								<option value="${team.id}">${team.name}</option>
							</c:if>
						</c:forEach>
					</select>
					<button data-championship-id="${championshipId}" data-round-id="${roundId}" data-group-id="${groupId}"
							onclick="bettingApp.addTeam(event)">+</button>
				</form>
			</div>
		</c:if>
	</div>
</div>

