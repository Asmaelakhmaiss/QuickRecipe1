package recetteController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "LikeDislikeServlet", urlPatterns = {"/LikeDislikeServlet"})
public class LikeDislikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession(false); // Récupère la session existante sans en créer une nouvelle
	    if (session != null) {
	        String username = (String) session.getAttribute("username"); // Récupère 'username' de la session
	        if (username != null) {
	            String idPara = request.getParameter("recipeid");
	            long recipe_id = Long.parseLong(idPara);
	            String action = request.getParameter("action");
	            if (recipe_id !=0 && action != null) {
	                recetteDAO dao = new recetteDAO();
	                if (dao.hasUserVoted(username, recipe_id)) {
                        String previousAction = dao.getUserVoteAction(username, recipe_id);

                        if (previousAction.equals(action)) {
                            // L'utilisateur veut annuler son vote
                            dao.removeVote(username, recipe_id);
                            if ("like".equals(action)) {
                                dao.decrementLikes(recipe_id);
                            } else if ("dislike".equals(action)) {
                                dao.decrementDislikes(recipe_id);
                            }
                        } else {
                            // L'utilisateur veut changer son vote
                            dao.updateVote(username, recipe_id, action);
                            if ("like".equals(previousAction) && "dislike".equals(action)) {
                                dao.decrementLikes(recipe_id);
                                dao.incrementDisLikes(recipe_id);
                            } else if ("dislike".equals(previousAction) && "like".equals(action)) {
                                dao.decrementDislikes(recipe_id);
                                dao.incrementLikes(recipe_id);
                            }
                        }
                    } else {
                        // L'utilisateur n'a pas encore voté
                        dao.addVote(username, recipe_id, action);
                        if ("like".equals(action)) {
                            dao.incrementLikes(recipe_id);
                        } else if ("dislike".equals(action)) {
                            dao.incrementDisLikes(recipe_id);
                        }
                    }

                    response.sendRedirect("details.jsp?id=" + recipe_id);
                    return;
                }
            } else {
                response.sendRedirect("login.jsp");
                return;
            }
        } else {
            response.sendRedirect("login.jsp");
            return;
        }
    }
}