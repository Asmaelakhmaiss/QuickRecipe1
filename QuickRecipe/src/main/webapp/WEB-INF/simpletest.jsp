<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Exemple JSP</title>
</head>
<body>
<h2>Message reçu du Servlet :</h2>
<p> vous avez entré: ${searchQuery}
</p> <!-- Affiche la valeur de 'message' passée par le servlet -->
</body>
</html>