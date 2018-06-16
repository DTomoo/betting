<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Bajnokágok</h1>

<c:if test="${not empty errorMessages}">
	<ul class="errorMessages">
		<c:forEach items="${errorMessages}" var="msg">
			<li>${errorMessages}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="table-responsive">
<table class="table table-hover">
	<thead>
		<c:if test="${admin}">
		<th class="col-sm-1">#</th>
		</c:if>
		<th colspan="${admin ? 1 : 2}" class="col-sm-8" >Bajnokság megnevezése</th>
		<c:if test="${admin}">
		<th class="col-sm-1"></th>
		</c:if>
	</thead>

	<c:if test="${not empty championships}">
		<c:forEach var="championship" items="${championships}">
			<tr data-championship-id="${championship.id}" onclick="bettingApp.championshipPage.expandChampionship(this)" style="cursor:pointer">
				<c:if test="${admin}">
				<td class="col-sm-1">${championship.id}</td>
				</c:if>
				
				<td colspan="${admin ? 1 : 2}" class="col-sm-8">${championship.name}</td>
				
				<c:if test="${admin}">
				<td class="col-sm-1">
						<input placeholder="Új forduló neve" onblur="bettingApp.addRound(this)" onkeyup="bettingApp.addRoundOnEnter(event,this)"></input>
				</td>
				</c:if>
			</tr>
		
			<tr style="display:${expandedChampionshipId eq championship.id ? 'table-row' : 'none'}; background-color:darkgrey; cursor:crosshair" data-round-of-championship-id="${championship.id}">
				<td></td>
				<td colspan="5"><button onclick="bettingApp.championshipPage.openMatchList(event, ${championship.id})">Részletek megjelenítése</button></td>
			</tr>
		
			<c:if test="${not empty championship.rounds}">
				
				
				<tr style="display:${expandedChampionshipId eq championship.id ? 'table-row' : 'none'}; background-color:lightgrey" data-round-of-championship-id="${championship.id}">
					<td class="col-sm-1">Fordulók</td>
					<td class="col-sm-8" colspan="2">
						<table class="table table-hover">
							<c:forEach var="round" items="${championship.rounds}">
								<tr data-round-id="${round.id}">
									<td class="col-sm-1"><c:if test="${admin}">${round.id}</c:if></td>
									<td class="col-sm-8">${round.name}</td>
									<c:if test="${admin}">
									<td class="col-sm-1">
											<input placeholder="Új csoport neve" onblur="bettingApp.addGroup(this)" onkeyup="bettingApp.addGroupOnEnter(event,this)"></input>
									</td>
									</c:if>
								</tr>
								
								<c:if test="${not empty round.groups}">
									<tr>
										<td class="col-sm-1">Csoportok</td>
										<td class="col-sm-8" colspan="2">
											<table class="table table-hover">
												<c:forEach var="group" items="${round.groups}">
													<tr data-group-id="${group.id}">
														<td><c:if test="${admin}">${group.id}</c:if></td>
														<td>${group.name}</td>
													</tr>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</table>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</c:if>



	<c:if test="${admin}">
		<td>+</td>
		<td colspan="10"><input placeholder="Új bajnokság neve" onkeyup="bettingApp.addChampionshipOnEnter(event);" onblur="bettingApp.addChampionship(this);"></input>
	</c:if>
</table>
</div>
