 package recetteController;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import management_recette.SingleConnexion;





public class recetteDAO {

    // Méthode pour rechercher des recettes en fonction d'un critère
    public List<recette> rechercherRecettes(String critere) {
        List<recette> resultats = new ArrayList<>();
        
        System.out.println("Search criteria: " + critere);
        
        try (Connection connection = SingleConnexion.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM quickrecipe WHERE titre LIKE ?")) {
            
            preparedStatement.setString(1, "%" + critere + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    recette recette = new recette();
                    recette.setId(resultSet.getLong("id"));
                    recette.setTitre(resultSet.getString("titre"));
                    recette.setIngredients(resultSet.getString("ingredients"));
                    recette.setInstructions(resultSet.getString("instructions"));
                    recette.setImagePath(resultSet.getString("image_name"));
                    recette.setNbrLike(resultSet.getInt("nbr_like"));
                    recette.setNbrDislike(resultSet.getInt("nbr_dislike"));
                    resultats.add(recette);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultats;
    }
    

    public recette getRecetteById(Long id) {
        recette recipe = null;
        try (Connection connection = SingleConnexion.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM quickrecipe WHERE id = ?")) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    recipe = new recette();
                    recipe.setId(resultSet.getInt("id"));
                    recipe.setTitre(resultSet.getString("titre"));
                    recipe.setIngredients(resultSet.getString("ingredients"));
                    recipe.setInstructions(resultSet.getString("instructions"));
                    recipe.setImagePath(resultSet.getString("image_name"));
                    recipe.setNbrLike(resultSet.getInt("nbr_like"));
                    recipe.setNbrDislike(resultSet.getInt("nbr_dislike"));
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipe;
    }
    
    
    public List<recette> getAllRecipes() {
        List<recette> recipes = new ArrayList<>();
        try (Connection connection = SingleConnexion.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
            		 "SELECT id, titre , ingredients , instructions , image_name , nbr_like , nbr_dislike FROM quickrecipe")) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
            	recette recette = new recette();
                recette.setId(rs.getLong("id"));
                recette.setTitre(rs.getString("titre"));
                recette.setIngredients(rs.getString("ingredients"));
                recette.setInstructions(rs.getString("instructions"));
                recette.setImagePath(rs.getString("image_name"));
                recette.setNbrLike(rs.getInt("nbr_like"));
                recette.setNbrDislike(rs.getInt("nbr_dislike"));
                recipes.add(recette);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return recipes;
    }
    public boolean incrementLikes(long id) {
        String query = "UPDATE quickrecipe SET nbr_like = nbr_like + 1 WHERE id = ?";
        
        try (Connection conn = SingleConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean incrementDisLikes(long id) {
        String query = "UPDATE quickrecipe SET nbr_dislike = nbr_dislike + 1 WHERE id = ?";
        
        try (Connection conn = SingleConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void decrementLikes(long id) {
        try (Connection connection = SingleConnexion.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE quickrecipe SET nbr_like = nbr_like - 1 WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void decrementDislikes(long id) {
        try (Connection connection = SingleConnexion.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE quickrecipe SET nbr_dislike = nbr_dislike - 1 WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean deleteRecetteById(long id) {
        String query = "DELETE FROM quickrecipe WHERE id = ?";
        try (Connection connection = SingleConnexion.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

}
    public String getImagePathById(long id) {
        String query = "SELECT image_name FROM quickrecipe WHERE id = ?";
        try (Connection connection = SingleConnexion.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("image_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updateRecipe(long id, String title, String ingredients, String instructions, String imageName) {
        String sql;
    	if (imageName != null && !imageName.isEmpty()) {
    	    sql = "UPDATE quickrecipe SET titre = ?, ingredients = ?, instructions = ?, image_name = ? WHERE id = ?";
    	} else {
    	    sql = "UPDATE quickrecipe SET titre = ?, ingredients = ?, instructions = ? WHERE id = ?";
    	}

    	try (Connection connection = SingleConnexion.getConnection();
    	     PreparedStatement statement = connection.prepareStatement(sql)) {

    	    statement.setString(1, title);
    	    statement.setString(2, ingredients);
    	    statement.setString(3, instructions);

    	    if (imageName != null) {
    	        statement.setString(4, imageName);
    	        statement.setLong(5, id);
    	    } else {
    	        statement.setLong(4, id);
    	    }

    	    int affectedRows = statement.executeUpdate();
    	    return affectedRows > 0;
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	    return false;
    	    // Gestion des erreurs de base de données
    	

            // Gestion des erreurs de base de données
        }
    }
public String getImageNameById(long id) {
        String query = "SELECT image_name FROM quickrecipe WHERE id = ?";
        try (Connection connection = SingleConnexion.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("image_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
public boolean hasUserVoted(String username, long recipe_id) {
    try (Connection connection = SingleConnexion.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT * FROM votes WHERE username = ? AND recipe_id = ?")) {

        preparedStatement.setString(1, username);
        preparedStatement.setLong(2, recipe_id);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next(); // Retourne true si une ligne est trouvée, false sinon
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erreur lors de la vérification du vote de l'utilisateur", e);
    }
}

public String getUserVoteAction(String username, long recipe_id) {
    try (Connection connection = SingleConnexion.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT action FROM votes WHERE username = ? AND recipe_id = ?")) {

        preparedStatement.setString(1, username);
        preparedStatement.setLong(2, recipe_id);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("action");
            } else {
                return null; // L'utilisateur n'a pas encore voté pour cette recette
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erreur lors de la récupération de l'action de vote de l'utilisateur", e);
    }
}

public void addVote(String username, long recipe_id, String action) {
    try (Connection connection = SingleConnexion.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "INSERT INTO votes (username, recipe_id, action) VALUES (?, ?, ?)")) {

        preparedStatement.setString(1, username);
        preparedStatement.setLong(2, recipe_id);
        preparedStatement.setString(3, action);

        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erreur lors de l'ajout du vote de l'utilisateur", e);
    }
}
public void updateVote(String username, long recipe_id, String action) {
    try (Connection connection = SingleConnexion.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "UPDATE votes SET action = ? WHERE username = ? AND recipe_id = ?")) {

        preparedStatement.setString(1, action);
        preparedStatement.setString(2, username);
        preparedStatement.setLong(3, recipe_id);

        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erreur lors de la mise à jour du vote de l'utilisateur", e);
    }
}

public void removeVote(String username, long recipe_id) {
    try (Connection connection = SingleConnexion.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "DELETE FROM votes WHERE username = ? AND recipe_id = ?")) {

        preparedStatement.setString(1, username);
        preparedStatement.setLong(2, recipe_id);

        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erreur lors de la suppression du vote de l'utilisateur", e);
    }
}

    
}
