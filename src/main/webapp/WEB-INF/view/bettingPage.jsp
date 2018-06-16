<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

<h1>Fogadás</h1>


<c:if test="${not empty matchData.errorMessages}">
	<ul class="errorMessages">
		<c:forEach var="msg" items="${matchData.errorMessages}">
			<li>${msg}</li>
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

		<c:set var="userIsStillModifiable" 
			   value="${(empty matchData.userBet or matchData.userBet.betStatus eq 'MODIFIABLE') and matchData.status eq 'NEW'}" />
		
		<c:set var="hasResult" value="${not empty matchData.result}" />   
		<c:set var="finalResult" value="${hasResult ? matchData.result.betPieces['FINAL_RESULT'] : ''}" />
					

		<div>
			<c:choose>
				<c:when test="${not userIsStillModifiable}">
					<h2>${matchData.team1.name} vs. ${matchData.team2.name}<c:if test="${not empty finalResult}"> (${finalResult})</c:if></h2>

					<c:if test="${empty matchData.userBet}">Erre a játékra nem tippeltél.</c:if>

				</c:when>
				<c:otherwise>
					<h2>${matchData.team1.name} vs. ${matchData.team2.name}</h2>

					<div class="jumbotron ">
					<form action="#" id="addBetForm" class="form-horizontal">
							<input type="hidden" name="matchId" value="${matchData.id}" />
							
							<c:forEach var="betPiece" items="${matchData.possibleBetPieces}">
							<div class="form-group">
								<label class="control-label col-sm-2">
									${	betPiece == 'SCORE_HOME' ? matchData.team1.name :
    									betPiece == 'SCORE_GUEST' ? matchData.team2.name : betPiece.getText() }
								</label>
								<div class="col-sm-10">
									<c:set var="betVal" value="${not empty matchData.userBet ? matchData.userBet.betPieces[betPiece.name()] : ''}" />
									
									<c:choose>
										<c:when test="${betPiece == 'WINNER'}">
											<label class="radio-inline "><input type="radio" name="bet[${betPiece.name()}]" value="${matchData.team1.id}" ${betVal eq matchData.team1.name ? 'checked' : ''}>${matchData.team1.name}</input></label>
											<label class="radio-inline "><input type="radio" name="bet[${betPiece.name()}]" value="${matchData.team2.id}" ${betVal eq matchData.team2.name ? 'checked' : ''}>${matchData.team2.name}</input></label>
											<label class="radio-inline "><input type="radio" name="bet[${betPiece.name()}]" value="" ${not empty matchData.userBet and empty betVal ? 'checked' : ''}>Döntetlen</input></label>
										</c:when>
										<c:when test="${betPiece == 'FINAL_RESULT'}">
											<div class="col-sm-2">
											<input class="form-control" type="text" name="resultScore1" placeholder="Hazai" value="${not empty betVal ? betVal.substring(0, betVal.indexOf(':')) : '' }"/>
											</div>
											<div class="col-sm-2">
											<input class="form-control" type="text" name="resultScore2" placeholder="Vendég" value="${not empty betVal ? betVal.substring(betVal.indexOf(':')+1) : '' }"/>
											</div>
										</c:when>
										<c:otherwise>
											<input class="form-control" type="text" name="bet[${betPiece.name()}]" placeholder="${betPiece.getText()}" value="${betVal}"/>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							</c:forEach>
							
					
					<div class="form-group"> 
						<div class="col-sm-offset-2 col-sm-10">
							<button id="addBetButton" class="btn btn-default" onclick="bettingApp.bettingPage.quickFilling(event, ${matchData.team1.id}, ${matchData.team2.id});">Gyors kitöltés a gólszámokból</button>
						</div>
					</div>
					<div class="form-group"> 
						<div class="col-sm-offset-2 col-sm-10">
							<c:choose>
								<c:when test="${empty matchData.userBet}">
									<button id="addBetButton" class="btn btn-danger" onclick="bettingApp.bettingPage.addNewBet(event);">Tipp elküldése</button>
								</c:when>
								<c:otherwise>
									<button id="editBetButton" class="btn btn-danger" onclick="bettingApp.bettingPage.editBet(event);">Tipp módosítása</button>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					</form>
					</div>

				</c:otherwise>
			</c:choose>
		</div>

		<div>

			<table class="table table-hover table-bordered matchTable">
				<thead>
					<tr>
						<th class="col-sm-2">Játékos</th>
						<th class="col-sm-2">Joker</div></th>
						<c:forEach var="betPiece" items="${matchData.possibleBetPieces}">
							<th class="col-sm-2">${betPiece.text}</th>
						</c:forEach>
						<th class="col-sm-2">Státusz</th>
						<c:if test="${hasResult}">
							<th class="col-sm-1">Pont</th>
						</c:if>
					</tr>
					
					<c:if test="${not empty matchData.result}">
						<th class="col-sm-2">Eredmény</th>
						<th class="col-sm-2"></div></th>
						<c:forEach var="betPiece" items="${matchData.possibleBetPieces}">
							<th class="col-sm-2">${matchData.result.betPieces[betPiece.name()]}</th>
						</c:forEach>
						<th class="col-sm-2"></th>
					</c:if>
					
					<c:if test="${hasResult}"><th class="col-sm-1"></th></c:if>
					
				</thead>
				
				<c:if test="${not empty matchData.userBet}">
					<tr class="table-active displayMode">
						<td>${matchData.userBet.owner.name}</td>
						<td>${matchData.userBet.joker ? 'X' : ''}</td>
						<c:forEach var="betPiece" items="${matchData.possibleBetPieces}">
							<td>${matchData.userBet.betPieces[betPiece.name()]}</td>
						</c:forEach>
						<td>${matchData.userBet.betStatus.text}</td>
						<c:if test="${hasResult}">
							<td class="col-sm-1">${matchData.userBet.score}</td>
						</c:if>
						
					</tr>
				</c:if>

				<c:if test="${not empty matchData.otherBets}">
					<c:forEach var="bet" items="${matchData.otherBets}">
						<tr>
							<td>${bet.owner.name}</td>
							<td>${userIsStillModifiable ? '?' : bet.joker ? 'X' : 'nincs'}</td>
							<c:forEach var="betPiece" items="${matchData.possibleBetPieces}">
								<td><c:if test="${not empty bet.betPieces[betPiece.name()]}">${userIsStillModifiable ? '?' : bet.betPieces[betPiece.name()]}</c:if></td>
							</c:forEach>
							<td>${userIsStillModifiable ? '?' : bet.betStatus.text}</td>
							<c:if test="${hasResult}">
								<td class="col-sm-1">${userIsStillModifiable ? '?' : bet.score}</td>
							</c:if>
						</tr>
					</c:forEach>
				</c:if>
			</table>

		</div>

	</c:otherwise>
</c:choose>
