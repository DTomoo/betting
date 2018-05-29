<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

<h1>Fogadás</h1>


<c:if test="${not empty matchData.errorMessages}">
	<ul class="errorMessages">
		<c:forEach items="${errorMessages}" var="msg">
			<li>${errorMessages}</li>
		</c:forEach>
	</ul>
</c:if>

<c:choose>
	<c:when test="${empty matchData.status}">
		<form action="/ds/matchDetails">
			Nincs ilyen meccs. Próbálj újat.<br />
			Azonosító: <input name="matchId" value="0" />
			<button type="submit">Küld</button>
		</form>
	</c:when>
	<c:otherwise>


		<div>
			<c:choose>
				<c:when test="${matchData.status.isFinished()}">
					<h2>${matchData.team1.name} vs. ${matchData.team2.name} (${matchData.gameStatistics.score1} - ${matchData.gameStatistics.score2})</h2>

					<c:if test="${empty matchData.userBet}">Erre a játékra nem tippeltél.</c:if>

				</c:when>
				<c:otherwise>
					<h2>${matchData.team1.name} vs. ${matchData.team2.name}</h2>

					<c:if test="${empty matchData.userBet}">Erre a játékra még nem tippeltél.
					
						<div class="jumbotron ">
						<form action="#" id="addBetForm">
								<input type="hidden" name="matchId" value="${matchData.matchId}" />
								
								<div class="input-group">
    								<span class="input-group-addon">${matchData.team1.name}</span>
    								<input id="msg" type="text" class="form-control" name="score1" placeholder="Lőtt gólok száma" />
								</div>
								<div class="input-group">
    								<span class="input-group-addon">${matchData.team2.name}</span>
    								<input id="msg" type="text" class="form-control" name="score2" placeholder="Lőtt gólok száma" />
								</div>
								
						</form>
						<button id="addBetButton" onclick="bettingApp.bettingPage.addNewBet(this);">TIPP!</button>
						</div>
					</c:if>

				</c:otherwise>
			</c:choose>
		</div>

		<div>

				<table class="table table-hover">
					<thead>
						<tr>
							<td>Játékos</td>
							<td>Góltipp 1</td>
							<td>Góltipp 2</td>
							<td>Státusz</td>
						</tr>
					</thead>
					
					
					 matchData.userBet.betStatus:${ matchData.userBet.betStatus }<br>
					${matchData.userBet.betStatus == 'MODIFIABLE'}					 

					<c:set var="userIsStillModifiable" 
						value="${empty matchData.userBet or matchData.userBet.betStatus == 'MODIFIABLE'}" />
					
					userIsStillModifiable: ${userIsStillModifiable}
					<c:if test="${not empty matchData.userBet}">
						<tr class="table-active displayMode">
							<td>${matchData.userBet.owner.name}</td>
							<td>${matchData.userBet.score1}</td>
							<td>${matchData.userBet.score2}</td>
							<td>
								<c:choose>
								<c:when test="${userIsStillModifiable}">
									<button onclick="bettingApp.bettingPage.openForEdit(this);">Szerkesztés</button>
								</c:when>
								<c:otherwise>
									${matchData.userBet.betStatus.text}
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<c:if test="${userIsStillModifiable}">
							<tr class="table-active editMode" style="display:none">
								<td>${matchData.userBet.owner.name}</td>
								<td><input name="score1" value="${matchData.userBet.score1}" /></td>
								<td><input name="score2" value="${matchData.userBet.score2}" /></td>
								<td><button match-id="${matchData.matchId}" user-id="${matchData.userBet.owner.id}"
											onclick="bettingApp.bettingPage.sendEditData(this);">Elküld</button></td>
							</tr>
						</c:if>
					</c:if>

					<c:if test="${not empty matchData.otherBets}">
						<c:forEach items="${matchData.otherBets}" var="bet">
							<tr>
								<td>${bet.owner.name}</td>
								<td>${userIsStillModifiable ? '?' : bet.score1}</td>
								<td>${userIsStillModifiable ? '?' : bet.score2}</td>
								<td>${userIsStillModifiable ? '?' : matchData.userBet.betStatus.text}</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>

		</div>

	</c:otherwise>
</c:choose>
