<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>${championship.name}</h1>

<c:if test="${not empty errorMessages}">
	<ul class="errorMessages">
		<c:forEach items="${errorMessages}" var="msg">
			<li>${errorMessages}</li>
		</c:forEach>
	</ul>
</c:if>


<c:if test="${not empty championship.rounds}">

	<div class="container">
		<div id="roundTabsDefinition" class="tabControl">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#details" data-url="/ds/championship?onlyDetails=true&championshipId=${championship.id}">A bajnokságról</a></li>
				<c:forEach var="round" items="${championship.rounds}" varStatus="roundStatus">
					<li>
						<a data-toggle="tab" href="#round${roundStatus.index}" data-url="/ds/championship/round?roundId=${round.id}">${round.name}</a>
					</li>
				</c:forEach>
			</ul>

			<div class="tab-content">
				<div id="#details" class="tab-pane active class=" tab-pane activecontainer">
					<div class="jumbotron">
						<h1>Válaszd ki a fordulót!</h1>
					</div>
				</div>
				<c:forEach var="round" items="${championship.rounds}" varStatus="roundStatus">
					<div id="#round${roundStatus.index}" class="tab-pane"></div>
				</c:forEach>
			</div>
		</div>
	</div>

	<script>
		bettingApp.tabControls.registerTab("roundTabsDefinition", false);
	</script>


</c:if>
