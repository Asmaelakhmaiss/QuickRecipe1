package recetteController;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.File;
import java.io.IOException;

/**
 * Servlet implementation class Delet1Servlet
 */
@WebServlet(name = "Delet1Servlet", urlPatterns = {"/Delet1Servlet"})
public class Delet1Servlet extends HttpServlet {
	private static final String IMAGE_DIR = "C:/Users/HP/eclipse-workspace/QuickRecipe/src/main/webapp/Images/";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delet1Servlet() {
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
	        if (idParam != null && !idParam.trim().isEmpty()) {
	            
	            recetteDAO dao = new recetteDAO();
	            Long id = Long.parseLong(idParam);
	            String image_name= dao.getImagePathById(id);
	            File file = new File(IMAGE_DIR+image_name);
	            if (file.exists() && !file.isDirectory()) {
	                boolean isDeleted = file.delete();
	                if (isDeleted) {
	                    // Le fichier a été supprimé avec succès
	                } else {
	                    // La suppression a échoué
	                }
	            } else {
	                // Le fichier n'existe pas ou est un répertoire
	            }
	            
	            if (dao.deleteRecetteById(id)) { // Supprime l'enregistrement de la base de données
	                success = true;
	            }
}
	        
	        
	        // Redirection avec un paramètre indiquant le succès de la suppression
	        response.sendRedirect("admin_Recipe_Management.jsp?deletionSuccess=" + success + "&deletedId=" + idParam);
	}

}
