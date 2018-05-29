<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
</style>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">



<link rel="stylesheet" id="coToolbarStyle"
	href="/resources/css/styles.css" type="text/css" />

</head>

<body ng-app="bettingApp" ng-controller="myCtrl">

	<div class="container">
		<div id="tabs">
			<ul class="nav nav-tabs" id="prodTabs">
				<li class="active"><a data-toggle="tab" href="#home" >Home</a></li>
				<li><a data-toggle="tab" href="#menu1" data-url="/ds/matchList">Mérkőzések</a></li>
				<li><a data-toggle="tab" href="#menu2" data-url="/ds/users">Fogadók</a></li>
			</ul>


			<div class="tab-content">
				<div id="home" class="tab-pane active">
					Házi fogadás
				</div>

				<div id="menu1" class="tab-pane">
				
				</div>
				<div id="menu2" class="tab-pane">
				</div>
			</div>
		</div>


	</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" ></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
		integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
		crossorigin="anonymous"></script>

	<script src="/resources/js/betting.js"></script>

</body>
</html>



