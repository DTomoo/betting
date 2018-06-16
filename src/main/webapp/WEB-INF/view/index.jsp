<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
</style>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet" id="coToolbarStyle" href="/resources/css/styles.css" type="text/css" />
</head>

<body ng-app="bettingApp" ng-controller="myCtrl">

	<div class="container">
		<div id="topNavigation" class="tabControl">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#home">Home</a></li>
				<li><a data-toggle="tab" href="#menu0" data-url="/ds/championship">Bajnokságok</a></li>
				<li><a data-toggle="tab" href="#menu1" data-url="/ds/users">Fogadók</a></li>
				<li><button id="backButton" onclick="bettingApp.guiControls.pageNav.goBack(event)" style="display: none" type="button" class="btn btn-info">Vissza az előző oldalra</button></li>
				<li class="logout"><button onclick="location.href='/login?logout'" type="button" class="btn btn-danger">Kijelentkezés</button></li>
			</ul>


			<div class="tab-content">
				<div id="home" class="tab-pane active container">
					<div class="jumbotron">
						<h1>Házi fogadás</h1>
					</div>					
				</div>
				<div id="menu0" class="tab-pane"></div>
				<div id="menu1" class="tab-pane"></div>
			</div>
			
		</div>
	</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

	<script src="/resources/js/betting.js"></script>

</body>
</html>



