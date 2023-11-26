package recetteController;

import java.io.IOException;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class rechercheServlet
 */
@WebServlet(name = "rechercheServlet", urlPatterns = {"/rechercheServlet"})
public class rechercheServlet extends HttpServlet {
	private recetteDAO recetteDAO = new recetteDAO();
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public rechercheServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	  // Logique pour récupérer les recettes en fonction des critères de recherche
        String critereRecherche = request.getParameter("critereRecherche");

        // Récupérer la liste des recettes depuis la base de données
        List<recette> listeResultats = recetteDAO.rechercherRecettes(critereRecherche);

        // Ajoutez la liste de résultats à la requête
        request.setAttribute("listeResultats", listeResultats);

        // Dispatcher vers la page de résultats de recherche
        request.getRequestDispatcher("recherche.jsp").forward(request, response);
    
    }
}