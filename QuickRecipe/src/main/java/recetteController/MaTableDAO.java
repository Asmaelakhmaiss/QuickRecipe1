package recetteController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public final class MaTableDAO {
	 
    private static final String query = "SELECT * FROM quickrecipe ";
    // Méthode pour récupérer toutes les lignes de la table quickrecipe
    public List<recette> getToutesLesLignes() {
        List<recette> listeRecettes = new ArrayList<>();
        try (Connection connection = SingleConnexion.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

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
                    listeRecettes.add(recette);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        
        return listeRecettes ;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
