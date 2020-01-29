<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>The Bad Date Bus</title>


<!-- PureCookie -->
<link rel="stylesheet" type="text/css" href="style.css" async />
<script src="script.js" async></script>
<!--            -->

</head>
<body>

	<div id="all">

	<h1>The Bad Date Bus</h1>
	<h3>Pick your bus stop</h3>
	<br>
	<img width="400" height="180"
		src="https://66.media.tumblr.com/db740ecf5a9be0e2c3f2e74dacd6541b/tumblr_mroaj81PsW1rka58bo1_500.gif"
		alt="Pic of a cat">
	<!-- <form action="Servlet1"> -->
	<!-- <select name="busStop"> -->
	<!-- <option>Malmö C</option> -->
	<!-- <option>Malmö Stadshuset</option> -->
	<!-- <option>Lund C</option> -->
	<!-- </select> -->
	<!-- <input type="submit" value="Get away!"> -->
	<!-- </form> -->




	<br>
	<br>
	<form action="BusStops">
		City:<input type="text" name="cityName"> <input type="submit"
			value="Search bus stops">
	</form>
	<br>
	<form action="Servlet2">
		<input type="submit" value="Your last searched bus">
	</form>
	<br>
	<form action="">
		<input type="submit" value="Repeat your latest search" hidden>
		<!-- Make invicible if there is no cookie -->
	</form>

</div>
</body>
</html>