<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

<h1>Betting page</h1>

<div>errorMessages: ${errorMessages}</div>
<div>match: ${match}</div>


<c:if test="${not empty errorMessages}">
	<ul class="errorMessages">
		<c:forEach items="${errorMessages}" var="msg">
			<li>${errorMessages}</li>
		</c:forEach>
	</ul>
</c:if>

<c:choose>
	<c:when test="${empty match}">
		<form action="/ds/betting/match">
			Nincs ilyen meccs. Próbálj újat.<br /> Azonosító: <input
				name="matchId" value="0" />
			<button type="submit">Küld</button>
		</form>
	</c:when>
	<c:otherwise>


		<div>
			<c:choose>
				<c:when test="${match.ended}">
					<h2>${match.team1.name}vs.${match.team2.name}
						(${match.gameStatistics.score1} - ${match.gameStatistics.score2})</h2>

					<c:if test="${empty userBet}">
		Erre a játékra nem tippeltél.
		</c:if>

				</c:when>
				<c:otherwise>
					<h2>${match.team1.name}vs.${match.team2.name}</h2>

					<c:if test="${empty userBet}">
					Erre a játékra még nem tippeltél.
					
					<form action="/ds/betting/addBet">
							<input type="hidden" name="matchId" value="${match.id}" />
							<div>
								score1: <input name="score1" />
							</div>
							<div>
								score2: <input name="score2" />
							</div>
							<div>
								<button type="submit">TIPP!</button>
							</div>
						</form>
					</c:if>

				</c:otherwise>
			</c:choose>
		</div>

		<div>
			<c:choose>
				<c:when test="${empty userBet and empty otherBets}">
		Erre a játékra egyetlen tipp sem érkezett.
	</c:when>
				<c:otherwise>

					<table>
						<thead>
							<tr>
								<td>User</td>
								<td>score1</td>
								<td>score2</td>
							</tr>
						</thead>

						<c:if test="${not empty userBet}">
							<tr class="myData">
								<td>${userBet.owner.name}</td>
								<td>${userBet.score1}</td>
								<td>${userBet.score2}</td>
							</tr>
						</c:if>

						<c:if test="${not empty otherBets}">
							<c:forEach items="${otherBets}" var="bet">
								<tr>
									<td>${bet.owner.name}</td>
									<td>${bet.score1}</td>
									<td>${bet.score2}</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>

				</c:otherwise>
			</c:choose>
		</div>

	</c:otherwise>
</c:choose>
