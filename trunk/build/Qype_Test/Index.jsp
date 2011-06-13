<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="/Qype_Test/SearchQype">
<label>Restaurant-Name:</label>
<input type="TEXT" name="location"></input>
<br>
<label>Stadt:</label>
<input type="TEXT" name="city"></input>
<br>
<input type="SUBMIT" value="Suchen"></input>
</form>

<form action="/Qype_Test/SearchNearby">
<label>Lat:</label>
<input type="TEXT" name="lat"></input>
<br>
<label>Long:</label>
<input type="TEXT" name="lng"></input>
<br>
<input type="SUBMIT" value="Suchen"></input>
</form>
</body>
</html>