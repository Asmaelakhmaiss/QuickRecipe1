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

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Recipe details</title>
    <link rel="stylesheet" href="accueil1.css"> <!-- Utilisez la même feuille de style que la page d'accueil -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <link rel="stylesheet" href="details1.css">
    <link rel="stylesheet" href="userhome1.css">
</head>

<body>
     <header id="primary-header">
        <div class="header-content">
         <div class="logo-container">
         <img src="Images/logo.png" alt="Logo" class="logo">
         <h1 >QuickRecipe</h1>
          </div>
        <% String username = (String) session.getAttribute("username");
             
            %>
          
   
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
     <% }else {%>
     <a href="login.jsp" class="btn-accueil">ADD A RECIPE</a>
     <%} %>
     
    </div>
    
        </section>
    <section class="details-section">
        <div class="recipe-details-container">
           <% 
    		String idPara = request.getParameter("id");
           
    		if (idPara != null && !idPara.isEmpty()) {
    			Long id = Long.parseLong(idPara);
        		recetteDAO recetteDAO = new recetteDAO();
        		recette recette = recetteDAO.getRecetteById(id);
			%>


            <div class="recipe-details-content">
                <h2><%= recette.getTitre() %></h2>
                
  





                <div class="recipe-details-header">
                    <img src="Images/<%= recette.getImagePath() %>" alt="<%= recette.getTitre() %>" >
                </div>
                              <div class="like-dislike-buttons">
                
    <form method="post" action="LikeDislikeServlet">
    <input type="hidden" name="recipeid" value="<%= recette.getId() %>">
    <input type="hidden" name="action" value="like">
    <input type="hidden" name="username" value="<%= username%>">
    <button type="submit" class="icon-button"><i class="fas fa-thumbs-up"></i></button>
    <span class="like-count"><%= recette.getNbrLike() %></span>
</form>
    <form method="post" action="LikeDislikeServlet">
    <input type="hidden" name="recipeid" value="<%= recette.getId() %>">
    <input type="hidden" name="action" value="dislike">
    <button type="submit" class="icon-button"><i class="fas fa-thumbs-down"></i></button>
    <span class="dislike-count"><%= recette.getNbrDislike() %></span>
</form>
</div>
                <div class="txt">
                <h3>Ingredients:</h3>
                <p><%= recette.getIngredients() %></p>

                <h3>Instructions :</h3>
                <p><%= recette.getInstructions() %></p>
                </div>
            </div>
   
            

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