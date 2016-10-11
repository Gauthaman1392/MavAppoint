<!DOCTYPE html>
<html>
<head>
	<title>MavAppoint</title>

	<meta name="description" content="Full view calendar component for twitter bootstrap with year, month, week, day views.">
	<meta name="keywords" content="jQuery,Bootstrap,Calendar,HTML,CSS,JavaScript,responsive,month,week,year,day">
	<meta http-equiv="CACHE-CONTROL" content="NO-CACHE">
	<meta charset="UTF-8">

	<link rel="stylesheet" href="components/bootstrap3/css/bootstrap.min.css">
	<link rel="stylesheet" href="components/bootstrap3/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="css/fullcalendar.css">
	
	<script type="text/javascript" src="components/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="components/underscore/underscore-min.js"></script>
	<script type="text/javascript" src="components/bootstrap3/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="components/jstimezonedetect/jstz.min.js"></script>
	<script type="text/javascript" src="js/lib/moment.min.js"></script>
	<script type="text/javascript" src="js/fullcalendar.js"></script>
	<script type="text/javascript" src="components/bootstrap3/js/bootstrap-datetimepicker.min.js"></script>
	<script>
		function startTime() {
		    var today = new Date();
		    var h = today.getHours();
		    var m = today.getMinutes();
		    var s = today.getSeconds();
		    m = checkTime(m);
		    s = checkTime(s);
		    document.getElementById('clock').innerHTML =
		    h + ":" + m + ":" + s;
		    var t = setTimeout(startTime, 500);
		}
		function checkTime(i) {
		    if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
		    return i;
		}
	</script>
	<style>
	body {padding-top:100px;padding-bottom:100px;background:url(img/engineering.jpg);background-size:cover;background-repeat:no-repeat;}
	.brand
	{
	  background:url(img/mavlogo.gif) no-repeat left center;
	  height:20px;
	  width:20px;
	}
	</style>
</head>
<body>
<div class="container">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div id="inversenavbar" class="container-fluid" style="background-color:#104E8B;">
			<div class="navbar-header">	
				<a class="navbar-brand" href="index"> <b> <font style="color:#e67e22" size="6"> MavAppoint </font></b></a>
			</div>