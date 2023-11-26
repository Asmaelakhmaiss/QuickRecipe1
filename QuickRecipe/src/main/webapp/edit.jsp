

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="recetteController.SingleConnexion"%>
<%@page import="recetteController.recetteDAO"%>
<%@page import="recetteController.recette"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
    <title>Edit recipe</title>
    <link rel="stylesheet" href="accueil1.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">


<style>

    .wrapper{
	position: relative;
   top: 17%;
    left: 0;
     /* A high value to ensure it's on top of other elements */
    width: 70%;
    margin-left:15%;
    gap:80px;
    height: auto; /* Set to auto to adjust the height based on content or set a specific height */
    /*background-color: rgba(255, 255, 255, 0.9); / Optional: Set a semi-transparent background */
}
    .form-container {
        margin: 0 auto;
        width: 50%;
        padding: 20px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    form {
        display: flex;
        flex-direction: column;
    }
    label {
        margin-top: 10px;
    }
    input[type="text"],
    textarea {
        padding: 10px;
        margin-top: 5px;
        border: 1px solid #ddd;
        border-radius: 5px;
    }
    button[type="submit"] {
        padding: 10px 20px;
        margin-top: 20px;
        background-color: #98968A;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    button[type="submit"]:hover {
        background-color: #98968A;
    }
    input[type="file"]::file-selector-button {
    display: none;}
    .current-image-container {
         width:30%;
         height:20%
        margin-top: 10px;
        margin-bottom: 5px;
    }

    .current-image-container img {
        width: 100%; /* Assurez-vous que l'image ne dépasse pas la largeur de son conteneur */
        height: auto; /* Conserve les proportions de l'image */
        border: 1px solid #ddd; /* Bordure légère pour l'image */
        border-radius: 4px; /* Bordures arrondies */
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Ombre subtile pour un effet de profondeur */
    }
}
    
</style>
</head>
<body>
<header>
        <div class="header-content">
         <div class="logo-container">
         <img src="Images/logo.png" alt="Logo" class="logo">
         <h1 >QuickRecipe</h1>
          </div>
        
    </div>
    
     

         </header>
         <section class="wrapper">
         <div class="form-container">
         <%
    		String idPara = request.getParameter("id");
           Long id = Long.parseLong(idPara);
    		if (idPara != null && !idPara.isEmpty()) {
        		recetteDAO recetteDAO = new recetteDAO();
        		recette recette = recetteDAO.getRecetteById(id);
			%>
    <form action="EditServlet" method="post" enctype="multipart/form-data">
         <input type="hidden" name="recipeid" value="<%= recette.getId() %>">
        <label for="recipeTitle">Recipe Name:</label>
        <input type="text" id="recipeTitle" name="recipeTitle" value="<%= recette.getTitre() %>" required>
        
        <label for="recipeInstructions">Ingredients:</label>
        <textarea id="recipeIngredients" name="recipeIngredients" rows="4" required><%= recette.getIngredients() %></textarea>
        
        <label for="recipeInstructions">Instructions:</label>
        <textarea id="recipeInstructions" name="recipeInstructions" rows="4" required><%= recette.getInstructions() %></textarea>
         <!-- Image actuelle -->
    <div class="current-image-container">
        <label>Current Image:</label>
        <img src="Images/<%= recette.getImagePath() %>" alt="Current Recipe Image" >
    </div>
    
    <!-- Champ pour télécharger une nouvelle image -->
    <label for="recipeImage">Recipe Image:</label>
    <input type="file" id="recipeImage" name="recipeImage" accept="image/*">
        
        <button type="submit" >Submit</button>
    </form>
    <% } %>
</div>
</section>
         



</body>
</html>