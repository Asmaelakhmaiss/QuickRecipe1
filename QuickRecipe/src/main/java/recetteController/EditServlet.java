package recetteController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class EditServlet
 * 
 */
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	    maxFileSize = 1024 * 1024 * 10,      // 10MB
	    maxRequestSize = 1024 * 1024 * 50    // 50MB
	)

public class EditServlet extends HttpServlet {
	private static final String IMAGE_DIR = "C:/Users/HP/eclipse-workspace/QuickRecipe/src/main/webapp/Images/";
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idParam = request.getParameter("recipeid");
		System.out.println(idParam);
    	long id = Long.parseLong(idParam);
    	recetteDAO dao = new recetteDAO();
    	String image_name = dao.getImageNameById(id);
	
	   String recipeTitle = request.getParameter("recipeTitle");
	   System.out.println(recipeTitle);
      String ingredients = request.getParameter("recipeIngredients");
    String instructions = request.getParameter("recipeInstructions");
    Part filePart = request.getPart("recipeImage"); // Récupère le fichier image
    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
    try {
        
    	String storagePath = IMAGE_DIR + fileName;

        filePart.write(storagePath);

        // Ajoutez ici le code pour gérer la réussite de l'opération
        // Par exemple, enregistrement des données de recette dans la base de données
    } catch (IOException e) {
        // Gestion des erreurs d'entrée/sortie
        e.printStackTrace(); // Imprime la trace de l'exception dans les logs de l'application
        // Vous pouvez également enregistrer l'erreur dans un fichier de log ou afficher un message d'erreur à l'utilisateur
    } 
    
    File file = new File(IMAGE_DIR+image_name);
     if (file.exists() && !file.isDirectory()&& fileName!=null && !fileName.isEmpty()) {
         file.delete();}
    recetteDAO dao2 = new recetteDAO();
    boolean success= dao2.updateRecipe(id, recipeTitle,ingredients,instructions, fileName );
   System.out.println( dao2.updateRecipe(id, recipeTitle,ingredients,instructions, fileName ));
    response.sendRedirect("admin_Recipe_Management.jsp?ConfirmationSuccess=" + success);
}
}
