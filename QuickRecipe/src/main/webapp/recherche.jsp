<%@page import="recetteController.SingleConnexion"%>
<%@page import="recetteController.recetteDAO"%>
<%@page import="recetteController.recette"%>
<%@page import="java.util.*"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
 
<%
    // Get the search criteria from the request parameter (adjust the parameter name accordingly)
    String critereRecherche = request.getParameter("critereRecherche");

    // Create an instance of RecetteDAO
    recetteDAO recetteDAO = new recetteDAO();

    // Populate listeResultats using the instance of RecetteDAO
    List<recette> listeResultats = recetteDAO.rechercherRecettes(critereRecherche);
%>

<%! 
    private String truncateIngredients(String ingredients) {
        int maxLength = 100; // Définissez la longueur maximale que vous souhaitez afficher
        if (ingredients.length() > maxLength) {
            return ingredients.substring(0, maxLength) + "...";
        } else {
            return ingredients;
        }
    }
%>
   
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search results</title>
    <link rel="stylesheet" href="accueil1.css">
    <link rel="stylesheet" href="userhome1.css"> <!-- Utilisez la même feuille de style que la page d'accueil -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <link rel="stylesheet" href="recherche1.css"> <!-- Nouvelle feuille de style pour les résultats de recherche -->
</head>

<body>
    <header id="primary-header">
        <div class="header-content">
         <div class="logo-container">
         <img src="Images/logo.png" alt="Logo" class="logo">
         <h1 >QuickRecipe</h1>
         <% String username = (String) session.getAttribute("username");
             
            %>
          </div>
        <% if (session ==null || username == null) { %>
          
          
        <div class="auth-buttons">
           <a href="login.jsp" class="button">log in </a>
           <a href="signup.jsp" class="button">sign up</a>
    </div>
    <% }  %>
    </div>
   

         </header>
         
<section class="nav">
           <div class="navigation">
            <a href="accueil.jsp" class="btn1">HOME</a>
            <a href="#info-bar" class="btn1">ABOUT</a>
            <a href=#info-bar class="btn-accueil">CONTACT</a>
            <% if (session !=null && username != null) { %>
            <a href="add_recipe.jsp" class="btn-accueil">ADD A RECIPE</a>
        <div class="dropdown">
        <button class="dropbtn">
            <i class="fa fa-user"></i> <!-- Icône d'utilisateur -->
            <i class="fa fa-chevron-down"></i> <!-- Icône de flèche vers le bas -->
        </button>
        <div class="dropdown-content">
            
            <a href="#" class="username"> <%= username %></a>
            <a href="#"><i class="fa fa-user"></i>My Profile</a>
            <a href="#"><i class="fa fa-cog"></i>Settings</a>
            <form action="LogoutServlet" method="post">
            <button type="submit" class="button"><i class="fa fa-sign-out-alt"></i> Log out</button>
            </form>
        </div>
       
    </div>
     <% } else { %>
      <a href="login.jsp" class="btn-accueil">ADD A RECIPE</a>
      <%} %>
        </div>
        
        </section>
         <section class="searching">
        <div class="search-container">
    <img src="Images/image1.png" alt="Background" class="background-image">
    <form class="search-box" action="rechercheServlet" method="get">
        <input type="text" placeholder="Search" name="critereRecherche">
        <button type="submit"><i class="fas fa-search"></i></button>
    </form>
    </div>
</section>


	<section class="resultat">
        <div class="recipe-gallery2">
            <% if (listeResultats.isEmpty() && critereRecherche != null) { %>
                <!-- Display error message for no results -->
                <div class="no-results-message">
                	<p class="error-msg1">No results for "<%= critereRecherche %>".</p>
                    <img src="Images/error-image.png" alt="Error">
                    <p class="error-msg2">Check the spelling or try another search.</p>
                </div>
            <% } else if (critereRecherche == null){ %>
                <!-- Display error message for no input -->
                <div class="no-input-message">
                    <img src="Images/error-image.png" alt="Error">
                    <p class="error-msg">Please enter a search term.</p>
                </div>
            <% } else { %>
            <% for (recette recette : listeResultats) { %>
    			<div class="recipe-card2">
        			<img src="Images/<%= recette.getImagePath() %>" alt="<%= recette.getTitre() %>">
    			</div>
    			<div class="recipe-details">
        			<div class="recipe-title"><%= recette.getTitre() %></div>
        			<p class="recipe-overview"><%= truncateIngredients(recette.getIngredients()) %></p>
        			 <a href="details.jsp?id=<%= recette.getId() %>" class="consult-button">View Recipe</a>
    			</div>
			<% } %>
            <% } %>
        </div>
    </section>
    <script>
function toggleDropdown() {
    var dropdownContent = document.getElementById("userDropdown");
    if (dropdownContent.style.display === "block") {
        dropdownContent.style.display = "none";
    } else {
        dropdownContent.style.display = "block";
    }
}
// Fermer le menu déroulant si l'utilisateur clique en dehors de celui-ci
window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.style.display === "block") {
                openDropdown.style.display = "none";
            }
        }
    }
}
</script>   
    
</body>
</html>