package management_recette;

import jakarta.servlet.ServletException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

/**
 * Servlet implementation class ConfirmServlet
 */
public class ConfirmServlet extends HttpServlet {
	private static final String Source= "C:/Users/HP/eclipse-workspace/QuickRecipe/src/main/webapp/Images_temp/";
	private static final String Destination = "C:/Users/HP/eclipse-workspace/QuickRecipe/src/main/webapp/Images/";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idParam = request.getParameter("id");
        boolean success = false;
        boolean success1 = false;
        
        if (idParam != null && !idParam.trim().isEmpty()) {
            long id = Long.parseLong(idParam);
            RecipeDAO dao = new RecipeDAO();
            String image_path= dao.getImagePathById(id);
            System.out.println(image_path);
            
            String titre= dao.getTitleById(id);
            String ingredients= dao.getIngredientsById(id);
            String instructions= dao.getInstructionsById(id);
            if (dao.confirmRecette(titre, ingredients, instructions, image_path)) { // Supprime l'enregistrement de la base de données
                success = true;
            }
            File sourcefile = new File(Source+image_path);
            File destinationfile = new File(Destination+image_path);
            if (sourcefile.exists() && !sourcefile.isDirectory()) {
            	 Files.copy(sourcefile.toPath(), destinationfile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            	 sourcefile.delete();
                
            } else {
                // Le fichier n'existe pas ou est un répertoire
            }
            if (dao.deleteRecetteById(id)) { // Supprime l'enregistrement de la base de données
                success1 = true;
            }
}
        
        
        
        // Redirection avec un paramètre indiquant le succès de la suppression
        response.sendRedirect("admin_Recipe_Validation.jsp?ConfirmationSuccess=" + (success&&success1) + "&ConfirmedId=" + idParam);
    }
	

}
