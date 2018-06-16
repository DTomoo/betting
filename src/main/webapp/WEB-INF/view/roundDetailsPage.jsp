<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>

<h2>${round.name}</h2>

<c:if test="${not empty errorMessages}">
	<ul class="errorMessages">
		<c:forEach items="${errorMessages}" var="msg">
			<li>${errorMessages}</li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${not empty round.groups}">

	<div class="panel-group">
		<c:forEach var="group" items="${round.groups}">
			<div class="panel panel-default">
				<div class="panel-heading">${group.id} - ${group.name}</div>
				<div class="panel-body">
					
					<c:if test="${admin}">
						<myTag:teamList headingText="<h3>${group.name}</h3>" teams="${group.teams}" teamsOfChampionship="${round.teamsOfChampionship}"
										championshipId="${round.championshipId}" roundId="${round.id}" groupId="${group.id}" />
					</c:if>
				
					<myTag:matchDetails matches="${group.matches}" teams="${group.teams}"
										championshipId="${round.championshipId}" roundId="${round.id}" groupId="${group.id}" />
				</div>
			</div>
		</c:forEach>
	</div>

</c:if>

