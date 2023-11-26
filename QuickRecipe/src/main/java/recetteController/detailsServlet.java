package recetteController;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import recetteController.recette;
import recetteController.recetteDAO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class detailsServlet
 */
@WebServlet(name = "detailsServlet", urlPatterns = {"/detailsServlet"})
public class detailsServlet extends HttpServlet {
	private recetteDAO recetteDAO = new recetteDAO();

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public detailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
  

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        // Récupérer le nom de l'image à partir des paramètres de la requête
    	String idParam = request.getParameter("id");
    	long id = Long.parseLong(idParam);
       
        

        // Récupérer la recette depuis la base de données en utilisant le nom de l'image
        recette recette = recetteDAO.getRecetteById(id);

        // Ajoutser la recette à la requête
        request.setAttribute("recette", recette);

        // Dispatcher vers la page details-recette.jsp
        request.getRequestDispatcher("details.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
